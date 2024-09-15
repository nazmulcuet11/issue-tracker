package com.issueTracker.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import com.issueTracker.entities.User
import com.issueTracker.services.interfaces.JwtService
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.config.ApplicationConfig
import java.util.Date

class JwtConfig(
    private val config: ApplicationConfig
) {
    private val secret = getConfigProperty("jwt.secret")
    val issuer = getConfigProperty("jwt.issuer")
    val audience = getConfigProperty("jwt.audience")
    val algorithm: Algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    val realm = getConfigProperty("jwt.realm")

    private fun getConfigProperty(path: String) = config.property(path).toString()
}

class JwtServiceImpl(
    private val config: JwtConfig
): JwtService {
    companion object {
        const val ACCESS_TOKEN_TTL_IN_MILLISECONDS = 15 * 60 * 1000L // 15 minutes
        const val REFRESH_TOKEN_TTL_IN_MILLISECONDS = 365 * 24 * 60 * 60 * 1000L // 365 days
    }

    override suspend fun generateAccessToken(user: User) =
        generateToken(user, ACCESS_TOKEN_TTL_IN_MILLISECONDS)

    override suspend fun generateRefreshToken(user: User) =
        generateToken(user, REFRESH_TOKEN_TTL_IN_MILLISECONDS)

    override suspend fun validate(credential: JWTCredential): JWTPrincipal? {
        if (extractUserId(credential.payload) == null ||
            extractUserEmail(credential.payload) == null ||
            credential.audience.contains(config.audience).not()) {
            return null
        }
        return JWTPrincipal(credential.payload)
    }

    private fun generateToken(
        user: User,
        ttl: Long
    ): String {
        return JWT
            .create()
            .withAudience(config.audience)
            .withIssuer(config.issuer)
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + ttl))
            .sign(config.algorithm)
    }

    private fun extractUserId(payload: Payload) = payload.getClaim("id").asInt()

    private fun extractUserEmail(payload: Payload) = payload.getClaim("email").asString()
}
