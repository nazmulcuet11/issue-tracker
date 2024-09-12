package com.issueTracker.dtos.extensions

import com.issueTracker.dtos.responses.IssueResponse
import com.issueTracker.entities.Issue

fun Issue.toDto(): IssueResponse {
    return IssueResponse(
        this.id,
        this.title,
        this.description,
        this.createdAt.time,
    )
}
