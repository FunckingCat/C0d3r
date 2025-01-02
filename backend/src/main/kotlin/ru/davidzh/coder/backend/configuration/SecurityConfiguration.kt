package ru.davidzh.coder.backend.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain
import ru.davidzh.coder.backend.configuration.converter.KeycloakJwtTokenConverter


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    private var keycloakJwtTokenConverter: KeycloakJwtTokenConverter? =
        KeycloakJwtTokenConverter(JwtGrantedAuthoritiesConverter())

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