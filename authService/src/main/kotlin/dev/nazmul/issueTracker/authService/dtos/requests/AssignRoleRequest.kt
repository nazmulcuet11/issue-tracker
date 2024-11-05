package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class AssignRoleRequest(
    val userId: Int,
    val roleId: Int,
)
