package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val accessToken: String
)
