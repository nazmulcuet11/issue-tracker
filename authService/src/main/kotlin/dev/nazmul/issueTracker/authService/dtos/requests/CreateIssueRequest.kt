package dev.nazmul.issueTracker.authService.dtos.requests

import kotlinx.serialization.Serializable

@Serializable
data class CreateIssueRequest(
    val title: String,
    val description: String?
)
