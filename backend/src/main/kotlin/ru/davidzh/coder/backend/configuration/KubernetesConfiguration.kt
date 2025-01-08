package ru.davidzh.coder.backend.configuration

import io.kubernetes.client.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class for setting up Kubernetes API client and environment.
 *
 * This class provides the necessary configuration to interact with a Kubernetes cluster.
 * It defines a bean to create a Kubernetes API client using the default configuration
 * and sets the orchestration namespace to "sandbox".
 */
@Configuration
class KubernetesConfiguration {

    /**
     * Provides the default Kubernetes API client configuration.
     *
     * This method returns a Kubernetes API client initialized with the default configuration,
     * allowing communication with the Kubernetes cluster.
     *
     * @return The configured Kubernetes API client.
     */
    @Bean
    internal fun apiClient() = Config.defaultClient()

    companion object {
        const val ORCHESTRATION_NAMESPACE = "sandbox"
    }

}