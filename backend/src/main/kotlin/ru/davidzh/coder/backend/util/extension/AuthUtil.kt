package ru.davidzh.coder.backend.util.extension

import jakarta.ws.rs.NotAllowedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import ru.davidzh.coder.backend.model.UserAuthentication

/**
 * Retrieves the authenticated user's details from the security context.
 *
 * This function attempts to extract the current user's authentication details from the
 * `SecurityContextHolder`. It expects the principal to be of type `Jwt`, which is then
 * converted to a `UserAuthentication` object. If the authentication details cannot be found
 * or if the principal is not of type `Jwt`, a `NotAllowedException` is thrown.
 *
 * @return the `UserAuthentication` object representing the current authenticated user.
 * @throws NotAllowedException if the authentication details cannot be found or if the principal
 *         is not a valid `Jwt`.
 */
fun getUserAuthentication() = try {
        (SecurityContextHolder
            .getContext()
            .authentication
            .principal as Jwt)
            .let { UserAuthentication.fromJwt(it) }
    } catch (e: Exception) {
        throw NotAllowedException("Authentication for user not found", e)
    }