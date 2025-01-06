package ru.davidzh.coder.backend.service

import io.kubernetes.client.openapi.ApiClient
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
import java.util.*

@Service
class KubernetesService(
) {

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

    fun getCronJobExecutionResult(userId: UUID, jobName: String): List<IntermediateJobState> =
        batchApi.listNamespacedJob("sandbox")
            .execute()
            .items
            .filter { it.metadata.name.startsWith(V1JobFactory.containerName(userId, jobName))}
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

    fun getPodLogs(podName: String) = coreApi
        .readNamespacedPodLog(podName, ORCHESTRATION_NAMESPACE)
        .execute().split("\n")

    fun getJobExecutionResult(userId: UUID, jobName: String): IntermediateJobState =
        getJobExecutionResult(V1JobFactory.containerName(userId, jobName))

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