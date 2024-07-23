package com.issueTracker.routes

import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.repositories.inMemory.InMemoryIssueRepository
import com.issueTracker.services.IssueService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// TODO: - Must be handled using DI
private val service = IssueService(InMemoryIssueRepository())

fun Route.configureOrderRoutes() {
    route("/api/v1/issue") {
        get {
            val command = com.issueTracker.commands.GetAllIssueCommand()
            val issues = service.getAllIssues(command)
            val issuesDto = issues.map { it.toDto() }
            call.respond(issuesDto)
        }
    }
}