package ru.davidzh.coder.backend.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration properties for Keycloak authentication and authorization setup.
 *
 * This class holds the properties necessary for connecting to a Keycloak server,
 * including the server URL, realm, client ID, and admin credentials (username and password).
 * These properties are loaded from the application's configuration files (e.g., application.yml).
 *
 * @property serverUrl The URL of the Keycloak server.
 * @property realm The name of the realm within Keycloak.
 * @property clientId The client ID to authenticate against Keycloak.
 * @property adminUsername The administrator's username to access Keycloak.
 * @property adminPassword The administrator's password to access Keycloak.
 */
@ConfigurationProperties(prefix = "keycloak")
data class KeycloakConfigurationProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val adminUsername: String,
    val adminPassword: String
)
