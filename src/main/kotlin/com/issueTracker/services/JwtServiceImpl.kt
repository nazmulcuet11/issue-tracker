package com.issueTracker.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.issueTracker.models.User
import com.issueTracker.services.interfaces.JwtService
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.config.ApplicationConfig
import java.util.*

class JwtServiceImpl(
    private val config: ApplicationConfig
) : JwtService {
    private companion object {
        const val ACCESS_TOKEN_TTL_IN_MILLISECONDS = 15 * 60 * 1000L // 15 minutes
        const val REFRESH_TOKEN_TTL_IN_MILLISECONDS = 365 * 24 * 60 * 60 * 1000L // 365 days
    }

    private val secret = getConfigProperty("jwt.secret")
    private val issuer = getConfigProperty("jwt.issuer")
    private val audience = getConfigProperty("jwt.audience")
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    override val realm = getConfigProperty("jwt.realm")

    override suspend fun generateAccessToken(user: User) =
        generateToken(user, ACCESS_TOKEN_TTL_IN_MILLISECONDS)

    override suspend fun generateRefreshToken(user: User) =
        generateToken(user, REFRESH_TOKEN_TTL_IN_MILLISECONDS)

    override suspend fun verify(token: String): DecodedJWT {
        return verifier.verify(token)
    }

    override suspend fun validate(credential: JWTCredential): JWTPrincipal? {
        if (audienceMatches(credential.audience).not()) {
            return null
        }
        return JWTPrincipal(credential.payload)
    }

    override suspend fun audienceMatches(audiences: List<String>) =
        audiences.contains(audience)

    private fun generateToken(
        user: User,
        ttl: Long
    ): String {
        return JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withClaim("role", user.role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + ttl))
            .sign(algorithm)
    }

    private fun getConfigProperty(path: String) = config.property(path).toString()
}
