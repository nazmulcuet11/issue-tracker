package com.issueTracker.services

import com.issueTracker.dtos.responses.CreateIssueRequest
import com.issueTracker.entities.Issue
import com.issueTracker.repositories.interfaces.IssueRepository
import com.issueTracker.services.interfaces.IssueService
import java.util.*

class IssueServiceImpl(
    private val repository: IssueRepository
): IssueService {
    override suspend fun getAllIssues(): List<Issue> {
        return repository.selectAll()
    }

    override suspend fun getIssueById(id: Int): Issue? {
        return repository.selectById(id)
    }

    override suspend fun createIssue(request: CreateIssueRequest): Issue? {
        val issue = Issue(
            id = 0,
            title = request.title,
            description = request.description,
            createdAt = Date()
        )
        return repository.insert(issue)
    }
}