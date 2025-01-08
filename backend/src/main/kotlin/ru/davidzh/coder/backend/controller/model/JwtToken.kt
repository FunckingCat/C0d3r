package ru.davidzh.coder.backend.controller.model

/**
 * Represents a JWT (JSON Web Token) used for authentication and authorization.
 *
 * This class holds the details of the JWT, including the access token, refresh token,
 * and the expiration time of the access token.
 *
 * @property accessToken The access token used for making authenticated requests.
 * @property refreshToken The refresh token used to obtain a new access token once it expires.
 * @property expiresIn The expiration time (in seconds) of the access token.
 */
data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
