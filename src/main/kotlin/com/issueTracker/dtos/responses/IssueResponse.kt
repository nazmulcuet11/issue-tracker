package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class IssueResponse(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: String,
)