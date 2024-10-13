package com.issueTracker.services.interfaces

import com.auth0.jwt.interfaces.DecodedJWT
import com.auth0.jwt.interfaces.JWTVerifier
import com.issueTracker.models.User
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

interface JwtService {
    val verifier: JWTVerifier
    val realm: String
    suspend fun generateAccessToken(user: User): String
    suspend fun generateRefreshToken(user: User): String
    suspend fun verify(token: String): DecodedJWT
    suspend fun validate(credential: JWTCredential): JWTPrincipal?
    suspend fun audienceMatches(audiences: List<String>): Boolean
}
