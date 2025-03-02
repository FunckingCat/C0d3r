package ru.davidzh.coder.backend.model

import org.springframework.security.oauth2.jwt.Jwt
import java.util.*

/**
 * Data class representing user authentication details.
 *
 * This class holds information related to the authentication of a user, including their unique ID
 * and the roles they are assigned within the system.
 *
 * @property userId the unique identifier of the user.
 * @property roles the set of roles assigned to the user, which define their permissions and access within the system.
 */
data class UserAuthentication(
    val userId: UUID,
    val roles: Set<Role>
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