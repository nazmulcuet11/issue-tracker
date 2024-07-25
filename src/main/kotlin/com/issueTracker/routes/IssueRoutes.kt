package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.services.interfaces.IIssueService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureOrderRoutes() {
    route("/api/v1/issue") {
        get {
            val command = com.issueTracker.commands.GetAllIssueCommand()
            val service = call.koinScope.get<IIssueService>()
            val issues = service.getAllIssues(command)
            val issuesDto = issues.map { it.toDto() }
            call.respond(issuesDto)
        }
    }
}