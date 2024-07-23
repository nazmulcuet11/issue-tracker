package com.issueTracker.repository

import com.issueTracker.domain.Issue

interface IssueRepository {
    fun getAllIssues(): List<Issue>
}