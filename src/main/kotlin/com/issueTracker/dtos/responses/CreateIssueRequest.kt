package com.issueTracker.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class CreateIssueRequest(
    val title: String,
    val description: String?
)