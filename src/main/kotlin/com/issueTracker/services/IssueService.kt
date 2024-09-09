package com.issueTracker.services

import com.issueTracker.commands.CreateIssueCommand
import com.issueTracker.commands.GetAllIssueCommand
import com.issueTracker.commands.GetIssueByIdCommand
import com.issueTracker.entities.Issue
import com.issueTracker.repositories.interfaces.IIssueRepository
import com.issueTracker.services.interfaces.IIssueService
import java.util.*

class IssueService(
    private val repository: IIssueRepository
): IIssueService {
    override suspend fun getAllIssues(command: GetAllIssueCommand): List<Issue> {
        return repository.selectAll()
    }

    override suspend fun getIssueById(command: GetIssueByIdCommand): Issue? {
        return repository.selectById(command.id)
    }

    override suspend fun createIssue(command: CreateIssueCommand): Issue? {
        val issue = Issue(
            id = 0,
            title = command.request.title,
            description = command.request.description ?: "",
            createdAt = Date()
        )
        return repository.insert(issue)
    }
}