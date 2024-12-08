package ru.davidzh.coder.backend.service

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.util.Config
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class KubernetesService(
    private val apiClient: ApiClient,
) {

    private val logger = LoggerFactory.getLogger(KubernetesService::class.java)

    @EventListener(ApplicationReadyEvent::class)
    fun doWork() {
        try {

            logger.info("Starting Kubernetes")

            val client = Config.defaultClient()
            val api = CoreV1Api(client)

            logger.info("Kubernetes API started")

            // List all pods
            api.listNamespacedPod("default")
                .execute()
                .items
                .forEach { logger.info("Pod ${it.metadata.name}") }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}