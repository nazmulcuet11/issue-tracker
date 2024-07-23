package com.issueTracker.dtos.responses

import java.util.*

data class IssueResponse(
    val id: String,
    val title: String,
    val description: String,
    val createdAt: Date,
)