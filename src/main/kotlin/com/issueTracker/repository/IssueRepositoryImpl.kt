package com.issueTracker.repository

import com.issueTracker.domain.Issue
import java.util.*

class IssueRepositoryImpl: IssueRepository {
    override fun getAllIssues(): List<Issue> {
        val issues = listOf(
            Issue("Issue 1", "Issue 1 description", Date()),
            Issue("Issue 2", "Issue 2 description", Date()),
            Issue("Issue 3", "Issue 3 description", Date()),
            Issue("Issue 4", "Issue 4 description", Date()),
            Issue("Issue 5", "Issue 5 description", Date()),
        )
        return issues
    }
}