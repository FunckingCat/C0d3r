package ru.davidzh.coder.backend.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "keycloak")
data class KeycloakConfigurationProperties(
    val serverUrl: String,
    val realm: String,
    val clientId: String,
    val adminUsername: String,
    val adminPassword: String
)
