package com.issueTracker.services.interfaces

import com.issueTracker.entities.User
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

interface JwtService {
    suspend fun generateAccessToken(user: User): String
    suspend fun generateRefreshToken(user: User): String
    suspend fun validate(credential: JWTCredential): JWTPrincipal?
}
