package ru.davidzh.coder.backend.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import ru.davidzh.coder.backend.configuration.converter.KeycloakJwtTokenConverter

/**
 * Security configuration class for setting up HTTP security and JWT authentication.
 *
 * This configuration class is responsible for setting up security-related features in the application,
 * including authorization rules, JWT authentication, and resource server settings.
 * It allows public access to certain endpoints (e.g., swagger UI, public API) while securing others,
 * ensuring that only authenticated users can access sensitive resources.
 * Additionally, CSRF protection is disabled, and JWT authentication is used for OAuth2 resource server.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    private var keycloakJwtTokenConverter: KeycloakJwtTokenConverter? =
        KeycloakJwtTokenConverter(JwtGrantedAuthoritiesConverter())

    /**
     * Configures the HTTP security for the application.
     *
     * This method sets up HTTP security for the web application, including:
     * - Permitting access to public endpoints (e.g., `/public`, Swagger-related paths).
     * - Securing other endpoints to require authentication.
     * - Disabling CSRF protection as it is not needed for stateless APIs.
     * - Configuring JWT-based OAuth2 resource server authentication using Keycloak.
     *
     * @param http The HttpSecurity instance used to configure the security.
     * @return The configured SecurityFilterChain.
     * @throws Exception If any errors occur while configuring the security filter chain.
     */
    @Bean
    @Throws(Exception::class)
    fun myServerFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorizeHttpRequests ->
                authorizeHttpRequests
                    .requestMatchers("/public/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .csrf { it.disable() }
        http.oauth2ResourceServer {
                oauth2 -> oauth2.jwt { jwt -> jwt.jwtAuthenticationConverter(keycloakJwtTokenConverter)
            }
        }
        return http.build()
    }
}