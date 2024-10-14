package com.issueTracker.auth.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String
)
