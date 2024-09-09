package com.issueTracker.commands

import com.issueTracker.dtos.responses.CreateIssueRequest

data class CreateIssueCommand(val request: CreateIssueRequest)