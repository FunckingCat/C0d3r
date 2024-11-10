package ru.davidzh.coder.backend.configuration

import org.keycloak.admin.client.Keycloak
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KeycloakConfiguration {

    @Bean
    internal fun keycloak(): Keycloak = Keycloak.getInstance(
        "http://localhost:8080",
        "users",
        "users-admin",
        "password",
        "backend-client"
    );

}