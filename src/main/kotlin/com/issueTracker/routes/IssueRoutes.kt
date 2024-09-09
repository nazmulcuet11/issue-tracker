package com.issueTracker.routes

import com.issueTracker.commands.CreateIssueCommand
import com.issueTracker.commands.GetIssueByIdCommand
import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.responses.CreateIssueRequest
import com.issueTracker.services.interfaces.IIssueService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
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

        post {
            val issueRequest = call.receive<CreateIssueRequest>()
            val command = CreateIssueCommand(issueRequest)
            val service = call.koinScope.get<IIssueService>()
            val issue = service.createIssue(command)
            if (issue == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            call.respond(issue.toDto())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val service = call.koinScope.get<IIssueService>()
            val command = GetIssueByIdCommand(id)
            val issue = service.getIssueById(command)
            if (issue == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(issue.toDto())
        }
    }
}