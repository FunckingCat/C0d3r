package ru.davidzh.coder.backend.configuration

import org.keycloak.admin.client.Keycloak
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.davidzh.coder.backend.configuration.properties.KeycloakConfigurationProperties

/**
 * Configuration class for setting up Keycloak authentication and authorization.
 *
 * This class is responsible for configuring and providing the Keycloak instance
 * with the necessary credentials and connection details, sourced from the
 * `KeycloakConfigurationProperties`.
 */
@Configuration
@EnableConfigurationProperties(KeycloakConfigurationProperties::class)
class KeycloakConfiguration(
    private val keycloakConfigurationProperties: KeycloakConfigurationProperties
) {

    /**
     * Provides a configured instance of the Keycloak client.
     *
     * This method creates and returns a Keycloak instance using the server URL, realm,
     * admin username, admin password, and client ID from the provided `KeycloakConfigurationProperties`.
     * This client instance can be used to interact with Keycloak for authentication and authorization tasks.
     *
     * @return The configured Keycloak client instance.
     */
    @Bean
    internal fun keycloak(): Keycloak = Keycloak.getInstance(
           keycloakConfigurationProperties.serverUrl,
           keycloakConfigurationProperties.realm,
           keycloakConfigurationProperties.adminUsername,
           keycloakConfigurationProperties.adminPassword,
           keycloakConfigurationProperties.clientId,
    );

}