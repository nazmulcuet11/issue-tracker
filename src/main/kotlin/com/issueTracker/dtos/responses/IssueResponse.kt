package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class IssueResponse(
    val id: Int,
    val title: String,
    val description: String?,
    val createdAt: Long,
)