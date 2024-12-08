package ru.davidzh.coder.backend.model

import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

data class UserAuthentication(
    val userId: UUID,
    val roles: Set<Role>,
) {
    companion object {
        fun fromJwt(jwt: Jwt): UserAuthentication = UserAuthentication(
            userId = UUID.fromString(jwt.claims["sub"] as String),
            roles = rolesListFromJwt(jwt)
        )

        private fun rolesListFromJwt(jwt: Jwt): Set<Role> {
            val realmAccess = jwt.claims["realm_access"] as Map<*, *>
            val roles = realmAccess["roles"] as List<*>
            return roles.mapNotNull { stringToRole(it as String) }.toSet()
        }

        private fun stringToRole(string: String): Role? = try {
            Role.valueOf(string)
        } catch (_: Exception) {
            null
        }
    }
}