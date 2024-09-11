package com.issueTracker.services.interfaces

import com.issueTracker.entities.Issue
import com.issueTracker.dtos.responses.CreateIssueRequest

interface IssueService {
    suspend fun getAllIssues(): List<Issue>
    suspend fun getIssueById(id: Int): Issue?
    suspend fun createIssue(request: CreateIssueRequest): Issue?
}