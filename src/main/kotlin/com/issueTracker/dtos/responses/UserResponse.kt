package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
)
