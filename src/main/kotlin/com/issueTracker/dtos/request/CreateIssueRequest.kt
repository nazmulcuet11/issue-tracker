package com.issueTracker.dtos.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateIssueRequest(
    val title: String,
    val description: String?
)
