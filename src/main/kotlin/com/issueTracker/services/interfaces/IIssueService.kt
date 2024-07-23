package com.issueTracker.services.interfaces

import com.issueTracker.entities.Issue
import com.issueTracker.commands.GetAllIssueCommand

interface IIssueService {
    suspend fun getAllIssues(command: GetAllIssueCommand): List<Issue>
}