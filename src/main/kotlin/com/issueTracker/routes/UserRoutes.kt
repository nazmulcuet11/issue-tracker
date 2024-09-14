package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.request.CreateUserRequest
import com.issueTracker.dtos.request.LoginRequest
import com.issueTracker.dtos.responses.LoginResponse
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

@Suppress("LongMethod")
fun Route.configureUserRoutes() {
    route("/api/v1/user") {
        post("/signup") {
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

        post("login") {
            val service = call.koinScope.get<JwtService>()
            val request = call.receive<LoginRequest>()
            val token = service.generateToken(request)
            if (token == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val response = LoginResponse(token)
            call.respond(response)
        }

        authenticate {
            get("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val service = call.koinScope.get<UserService>()
                val id = principal?.payload?.getClaim("id")?.asInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                val user = service.getUserById(id)
                if (user == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                call.respond(user.toDto())
            }
        }
    }

    // todo authenticate
    route("/api/v1/user") {
        get {
            val service = call.koinScope.get<UserService>()
            val users = service.getAllUsers()
            val usersDto = users.map { it.toDto() }
            call.respond(usersDto)
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
