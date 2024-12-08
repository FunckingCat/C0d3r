package ru.davidzh.coder.backend.util.extension

import jakarta.ws.rs.NotAllowedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import ru.davidzh.coder.backend.model.UserAuthentication

fun getUserAuthentication() = try {
        (SecurityContextHolder
            .getContext()
            .authentication
            .principal as Jwt)
            .let { UserAuthentication.fromJwt(it) }
    } catch (e: Exception) {
        throw NotAllowedException("Authentication for user not found", e)
    }