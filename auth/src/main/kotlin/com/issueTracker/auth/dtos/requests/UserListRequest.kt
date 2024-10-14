package com.issueTracker.auth.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserListRequest(
    val offset: Long,
    val limit: Int,
)
