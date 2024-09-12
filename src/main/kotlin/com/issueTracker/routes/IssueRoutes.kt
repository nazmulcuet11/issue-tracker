package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.request.CreateIssueRequest
import com.issueTracker.services.interfaces.IssueService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.configureIssueRoutes() {
    // todo authenticate
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
            val issue = service.createIssue(request.title, request.description)
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
