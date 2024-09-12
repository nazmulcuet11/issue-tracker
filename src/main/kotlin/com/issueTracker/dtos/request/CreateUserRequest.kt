package com.issueTracker.dtos.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)
