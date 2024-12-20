package dev.nazmul.issueTracker.authService.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
)
