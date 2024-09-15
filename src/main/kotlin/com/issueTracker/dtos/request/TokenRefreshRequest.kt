package com.issueTracker.dtos.request

import kotlinx.serialization.Serializable

@Serializable
data class TokenRefreshRequest(
    val refreshToken: String
)
