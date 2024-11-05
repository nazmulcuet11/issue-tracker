package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateRoleRequest(
    val name: String,
    val description: String?,
)
