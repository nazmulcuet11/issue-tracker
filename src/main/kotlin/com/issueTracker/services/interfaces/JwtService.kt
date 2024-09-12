package com.issueTracker.services.interfaces

import com.auth0.jwt.interfaces.JWTVerifier
import com.issueTracker.dtos.request.LoginRequest
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

interface JwtService {
    val realm: String
    val verifier: JWTVerifier
    suspend fun generateToken(request: LoginRequest): String?
    suspend fun validate(credential: JWTCredential): JWTPrincipal?
}
