package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
