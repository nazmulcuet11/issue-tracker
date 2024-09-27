package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class RoleResponse(
    val id: Int,
    val name: String,
    val description: String?,
)
