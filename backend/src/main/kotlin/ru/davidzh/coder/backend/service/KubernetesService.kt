package ru.davidzh.coder.backend.service

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.apis.BatchV1Api
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service
import ru.davidzh.coder.backend.configuration.KubernetesConfiguration.Companion.ORCHESTRATION_NAMESPACE
import ru.davidzh.coder.backend.factory.V1JobFactory
import ru.davidzh.coder.backend.model.JobParameters

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
        private val logger = LoggerFactory.getLogger(KubernetesService::class.java)
    }

}