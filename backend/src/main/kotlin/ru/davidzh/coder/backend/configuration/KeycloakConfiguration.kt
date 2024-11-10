package ru.davidzh.coder.backend.configuration

import org.keycloak.admin.client.Keycloak
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.davidzh.coder.backend.configuration.properties.KeycloakConfigurationProperties

@Configuration
@EnableConfigurationProperties(KeycloakConfigurationProperties::class)
class KeycloakConfiguration(
    private val keycloakConfigurationProperties: KeycloakConfigurationProperties
) {

    @Bean
    internal fun keycloak(): Keycloak = Keycloak.getInstance(
           keycloakConfigurationProperties.serverUrl,
           keycloakConfigurationProperties.realm,
           keycloakConfigurationProperties.adminUsername,
           keycloakConfigurationProperties.adminPassword,
           keycloakConfigurationProperties.clientId,
    );

}