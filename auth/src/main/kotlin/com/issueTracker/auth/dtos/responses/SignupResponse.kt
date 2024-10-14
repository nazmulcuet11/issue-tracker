package com.issueTracker.auth.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class SignupResponse(
    val userId: Int,
    val accessToken: String,
    val refreshToken: String,
)
