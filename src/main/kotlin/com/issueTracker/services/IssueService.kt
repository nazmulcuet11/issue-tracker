package com.issueTracker.services

import com.issueTracker.commands.GetAllIssueCommand
import com.issueTracker.entities.Issue
import com.issueTracker.repositories.interfaces.IIssueRepository
import com.issueTracker.services.interfaces.IIssueService

class IssueService(
    private val repository: IIssueRepository
): IIssueService {
    override suspend fun getAllIssues(command: GetAllIssueCommand): List<Issue> {
        return repository.selectAll()
    }
}