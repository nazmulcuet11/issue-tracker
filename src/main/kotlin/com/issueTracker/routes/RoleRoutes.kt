package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.requests.CreateRoleRequest
import com.issueTracker.mappers.modelsToDtos.toDto
import com.issueTracker.routes.extensions.authorized
import com.issueTracker.services.interfaces.RoleService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

@Suppress("LongMethod")
fun Route.configureRoleRoutes() {
    route("/api/v1/role") {
        authenticate {
            authorized("admin") {
                post {
                    val service = call.koinScope.get<RoleService>()
                    val request = call.receive<CreateRoleRequest>()
                    val role = service.createRole(request)
                    if (role != null) {
                        call.respond(HttpStatusCode.Created, role.toDto())
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "Could not create role")
                    }
                }
            }
        }
    }
}
