package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
