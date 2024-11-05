package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserListRequest(
    val offset: Long,
    val limit: Int,
)
