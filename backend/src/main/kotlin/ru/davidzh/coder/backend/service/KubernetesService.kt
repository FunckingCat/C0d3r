package ru.davidzh.coder.backend.service

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.apis.BatchV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1JobStatus
import io.kubernetes.client.util.Config
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.configuration.KubernetesConfiguration.Companion.ORCHESTRATION_NAMESPACE
import ru.davidzh.coder.backend.factory.V1JobFactory
import ru.davidzh.coder.backend.model.ContainerState
import ru.davidzh.coder.backend.model.ExecutionStatus
import ru.davidzh.coder.backend.model.IntermediateJobState
import ru.davidzh.coder.backend.model.JobParameters
import java.time.LocalDateTime

@Service
class KubernetesService {

    private val apiClient: ApiClient = Config.defaultClient()
    private val coreApi: CoreV1Api = CoreV1Api(apiClient)
    private val batchApi: BatchV1Api = BatchV1Api(apiClient)

    fun startJob(jobParameters: JobParameters) {
        V1JobFactory.createV1Job(jobParameters)
            .also { logger.info("Job created $it") }
            .let {
                batchApi.createNamespacedJob(ORCHESTRATION_NAMESPACE, it)
                    .pretty("true")
                    .execute()
            }
    }

    fun startCronJob(jobParameters: JobParameters) {
        V1JobFactory.createV1CronJob(jobParameters)
            .also { logger.info("Cron Job created $it") }
            .let {
                batchApi.createNamespacedCronJob(ORCHESTRATION_NAMESPACE, it)
                    .pretty("true")
                    .execute()
            }
    }

    fun getCronJobExecutionResult(containerName: String): List<IntermediateJobState> =
        batchApi.listNamespacedJob("sandbox")
            .execute()
            .items
            .filter { it.metadata.name.startsWith(containerName)}
            .map { it.metadata.name }
            .map { getJobExecutionResult(it) }

    fun getJobExecutionResult(fullJobName: String): IntermediateJobState {
        val job = batchApi.readNamespacedJob(fullJobName, ORCHESTRATION_NAMESPACE).execute()

        val containerStates: List<ContainerState> = coreApi.listNamespacedPod(ORCHESTRATION_NAMESPACE)
            .execute()
            .items
            .filter { it.metadata.labels[JOB_NAME_LABEL] == fullJobName }
            .map {
                ContainerState(
                    containerName = it.metadata.name,
                    status = ExecutionStatus.COMPLETED, // TODO Ставить корректный статус
                    checkTime = LocalDateTime.now(),
                    logs = getPodLogs(it.metadata.name),
                )
            }

        return IntermediateJobState(
            originalJobName = job.metadata.name,
            jobName = fullJobName,
            status = mapK8sJobStatusToExecutionStatus(job.status),
            containerStates = containerStates
        )
    }

    fun getPodLogs(podName: String): List<String> {
        try {
            val logs = coreApi.readNamespacedPodLog(podName, ORCHESTRATION_NAMESPACE).execute()?: ""
            return  logs.split("\n")
        } catch (e: Exception) {
            logger.error("Filed to get logs from pod {} {}", podName, e.message)
            return emptyList()
        }
    }

    fun mapK8sJobStatusToExecutionStatus(k8sJobStatus: V1JobStatus): ExecutionStatus {
        return if (k8sJobStatus.active == 1) {
             ExecutionStatus.RUNNING
        } else if (k8sJobStatus.succeeded != null && k8sJobStatus.succeeded > 0) {
             ExecutionStatus.COMPLETED
        } else if (k8sJobStatus.failed != null && k8sJobStatus.failed > 0) {
             ExecutionStatus.FAILED
        } else if (k8sJobStatus.terminating == 1) {
             ExecutionStatus.CANCELLED
        } else  ExecutionStatus.PENDING
    }

    /**
     * Deletes a job from Kubernetes by its name.
     *
     * @param jobName the name of the job to delete.
     * @return true if the job was successfully deleted, false otherwise.
     * @throws ApiException if there is an error during the deletion process.
     */
    fun deleteJob(jobName: String): Unit {
        try {
            batchApi.deleteNamespacedJob(
                jobName,
                ORCHESTRATION_NAMESPACE
            ).execute()
            logger.debug("Job $jobName successfully deleted.")
        } catch (e: Exception) {
            logger.debug("Job $jobName not exists.")
        }
    }

    /**
     * Terminates a Kubernetes job by its name.
     *
     * This method deletes the specified job from the Kubernetes cluster.
     * It ensures that the job and its associated pods are removed.
     *
     * @param jobName The name of the job to terminate.
     * @throws ApiException If there is an error communicating with the Kubernetes API.
     */
    fun terminateJob(jobName: String) {
        try {
            batchApi.deleteNamespacedJob(jobName, ORCHESTRATION_NAMESPACE)
                .execute()
            logger.debug("Job '$jobName' successfully terminated in namespace.")
        } catch (e: ApiException) {
            logger.debug("Error while terminating job '$jobName': ${e.responseBody}")
            throw e
        }
    }

    /**
     * Terminates a Kubernetes cron job by its name.
     *
     * This method deletes the specified job from the Kubernetes cluster.
     * It ensures that the job and its associated pods are removed.
     *
     * @param jobName The name of the job to terminate.
     * @throws ApiException If there is an error communicating with the Kubernetes API.
     */
    fun terminateCronJob(jobName: String) {
        try {
            batchApi.deleteNamespacedCronJob(jobName, ORCHESTRATION_NAMESPACE)
                .execute()
            logger.debug("Cron Job '$jobName' successfully terminated in namespace.")
        } catch (e: ApiException) {
            logger.debug("Error while terminating cron job '$jobName': ${e.responseBody}")
            throw e
        }
    }


    @EventListener(ApplicationReadyEvent::class)
    fun listAllPodsOnStartUp() {
        try {
            logger.info("Kubernetes API started")
            coreApi.listNamespacedPod(ORCHESTRATION_NAMESPACE)
                .execute()
                .items
                .forEach { logger.info("Pod ${it.metadata.name}") }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val JOB_NAME_LABEL = "job-name"
        private val logger = LoggerFactory.getLogger(KubernetesService::class.java)
    }

}