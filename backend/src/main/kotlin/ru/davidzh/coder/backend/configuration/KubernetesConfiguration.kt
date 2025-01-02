package ru.davidzh.coder.backend.configuration

import io.kubernetes.client.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KubernetesConfiguration {

    @Bean
    internal fun apiClient() = Config.defaultClient()

    companion object {
        const val ORCHESTRATION_NAMESPACE = "sandbox"
    }

}