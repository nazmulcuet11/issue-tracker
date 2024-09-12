package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.request.CreateUserRequest
import com.issueTracker.services.interfaces.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.configureUserRoutes() {
    // todo authenticate
    route("/api/v1/user") {
        get {
            val service = call.koinScope.get<UserService>()
            val users = service.getAllUsers()
            val usersDto = users.map { it.toDto() }
            call.respond(usersDto)
        }

        post {
            val service = call.koinScope.get<UserService>()
            val request = call.receive<CreateUserRequest>()
            val user = service.createUser(
                request.firstName,
                request.lastName,
                request.email,
                request.password
            )
           if (user == null) {
               call.respond(HttpStatusCode.BadRequest)
               return@post
           }
            call.respond(user.toDto())
        }

        // todo authenticate
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val service = call.koinScope.get<UserService>()
            val user = service.getUserById(id)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(user.toDto())
        }
    }
}
