package com.issueTracker.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class UserListRequest(
    val offset: Long,
    val limit: Int,
)
