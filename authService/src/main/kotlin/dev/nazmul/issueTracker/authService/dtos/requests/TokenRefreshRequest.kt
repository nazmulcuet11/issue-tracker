package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String
)
