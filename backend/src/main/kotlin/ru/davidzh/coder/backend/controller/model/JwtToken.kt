package ru.davidzh.coder.backend.controller.model

import io.swagger.v3.oas.annotations.media.Schema

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
@Schema(description = "Represents authentication tokens (JWT) issued upon successful login or registration. Contains tokens necessary for accessing protected resources and refreshing the session.")
data class JwtToken(

    @field:Schema(
        description = "The primary JSON Web Token (JWT) used to authenticate subsequent API requests. Include this token in the 'Authorization: Bearer <token>' header.",
        requiredMode = Schema.RequiredMode.REQUIRED, // This field is essential
        example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
    )
    val accessToken: String,

    @field:Schema(
        description = "A secondary token used to obtain a new access token without requiring the user to re-authenticate, once the original access token expires.",
        requiredMode = Schema.RequiredMode.REQUIRED, // Typically required if refresh functionality is supported
        example = "def456-abc123-xyz789-pqr000-long-refresh-token-string"
    )
    val refreshToken: String,

    @field:Schema(
        description = "The duration (in seconds) for which the `accessToken` is valid from the time it was issued.",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "3600"
    )
    val expiresIn: Long
)
