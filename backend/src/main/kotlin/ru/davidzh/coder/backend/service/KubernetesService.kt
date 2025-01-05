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

    fun getExecutionResult(userId: UUID, jobName: String): IntermediateJobState {

        val fullJobName = V1JobFactory.containerName(userId, jobName)

        val job = batchApi.readNamespacedJob(fullJobName, ORCHESTRATION_NAMESPACE).execute()

        val podList = coreApi.listNamespacedPod(ORCHESTRATION_NAMESPACE)
            .execute()
            .items
            .filter { it.metadata.labels[JOB_NAME_LABEL] == fullJobName }

        val logs = mutableListOf<String>()

        podList.forEach { pod ->
            try {
                val podLogs = coreApi.readNamespacedPodLog(pod.metadata.name, ORCHESTRATION_NAMESPACE).execute()
                logs.add(podLogs)
            } catch (e: Exception) {
                logs.add("Error fetching logs for pod: ${pod.metadata.name}")
            }
        }

        return IntermediateJobState(
            jobName = jobName,
            status = mapK8sJobStatusToExecutionStatus(job.status),
            checkTime = LocalDateTime.now(),
            logs = logs
        )
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
        }else  ExecutionStatus.PENDING
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