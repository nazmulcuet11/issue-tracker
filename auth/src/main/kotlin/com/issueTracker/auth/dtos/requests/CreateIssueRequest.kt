package com.issueTracker.auth.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateIssueRequest(
    val title: String,
    val description: String?
)
