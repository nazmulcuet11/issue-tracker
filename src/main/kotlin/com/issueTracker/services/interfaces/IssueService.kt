package com.issueTracker.services.interfaces

import com.issueTracker.commands.CreateIssueCommand
import com.issueTracker.entities.Issue
import com.issueTracker.commands.GetAllIssueCommand
import com.issueTracker.commands.GetIssueByIdCommand

interface IssueService {
    suspend fun getAllIssues(command: GetAllIssueCommand): List<Issue>
    suspend fun getIssueById(command: GetIssueByIdCommand): Issue?
    suspend fun createIssue(command: CreateIssueCommand): Issue?
}