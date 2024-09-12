package com.issueTracker.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import com.issueTracker.constants.JWT_TOKEN_TTL_IN_MILLISECONDS
import com.issueTracker.dtos.request.LoginRequest
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.config.ApplicationConfig
import java.util.*
import org.springframework.security.crypto.bcrypt.BCrypt

class JwtServiceImpl(
    private val config: ApplicationConfig,
    private val userService: UserService
): JwtService {
    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    private val algorithm = Algorithm.HMAC256(secret)

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    override val realm = getConfigProperty("jwt.realm")

    override suspend fun generateToken(request: LoginRequest): String? {
        val user = userService.getUserByEmail(request.email) ?: return null
        if (!BCrypt.checkpw(request.password, user.passwordHash)) {
            return null
        }
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + JWT_TOKEN_TTL_IN_MILLISECONDS))
            .sign(algorithm)
    }

    override suspend fun validate(credential: JWTCredential): JWTPrincipal? {
        val id = extractUserId(credential.payload) ?: return null
        if (userService.getUserById(id) == null) {
            return null
        }
        if (credential.audience.contains(audience).not()) {
            return null
        }
        return JWTPrincipal(credential.payload)
    }

    private fun getConfigProperty(path: String) = config.property(path).toString()
    private fun extractUserId(payload: Payload) = payload.getClaim("id").asInt()
}
