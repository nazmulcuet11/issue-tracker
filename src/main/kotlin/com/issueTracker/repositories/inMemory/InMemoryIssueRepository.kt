package com.issueTracker.repositories.inMemory

import com.issueTracker.entities.Issue
import com.issueTracker.repositories.interfaces.IIssueRepository
import java.util.*

class InMemoryIssueRepository: IIssueRepository {
    override suspend fun getAll(): List<Issue> {
        val issues = mutableListOf<Issue>()
        for (i in 1..10) {
            issues.add(
                Issue(
                    id = i.toString(),
                    "Issue $i",
                    "Description $i",
                    Date()
                )
            )
        }
        return issues
    }
}