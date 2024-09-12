package com.issueTracker.services.interfaces

import com.issueTracker.entities.Issue

interface IssueService {
    suspend fun getAllIssues(): List<Issue>
    suspend fun getIssueById(id: Int): Issue?
    suspend fun createIssue(title: String, description: String?): Issue?
}
