package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.responses.CreateIssueRequest
import com.issueTracker.services.interfaces.IssueService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureOrderRoutes() {
    route("/api/v1/issue") {
        get {
            val service = call.koinScope.get<IssueService>()
            val issues = service.getAllIssues()
            val issuesDto = issues.map { it.toDto() }
            call.respond(issuesDto)
        }

        post {
            val request = call.receive<CreateIssueRequest>()
            val service = call.koinScope.get<IssueService>()
            val issue = service.createIssue(request)
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
            val service = call.koinScope.get<IssueService>()
            val issue = service.getIssueById(id)
            if (issue == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(issue.toDto())
        }
    }
}