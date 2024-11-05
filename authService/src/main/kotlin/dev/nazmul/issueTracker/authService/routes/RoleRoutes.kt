package dev.nazmul.issueTracker.authService.routes

import dev.nazmul.issueTracker.authService.di.koinScope
import dev.nazmul.issueTracker.authService.dtos.requests.CreateRoleRequest
import dev.nazmul.issueTracker.authService.dtos.responses.RoleResponse
import dev.nazmul.issueTracker.authService.mappers.modelsToDtos.toDto
import dev.nazmul.issueTracker.authService.routes.extensions.authorized
import dev.nazmul.issueTracker.authService.services.interfaces.RoleService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
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

                get {
                    val service = call.koinScope.get<RoleService>()
                    val roles = service.getRoles(0, Int.MAX_VALUE)
                    val roleDTOs: List<RoleResponse> = roles.map { it.toDto() }
                    call.respond(roleDTOs)
                }
            }
        }
    }
}
