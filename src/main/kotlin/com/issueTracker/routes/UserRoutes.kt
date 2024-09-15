package com.issueTracker.routes

import com.issueTracker.di.koinScope
import com.issueTracker.dtos.extensions.toDto
import com.issueTracker.dtos.request.LoginRequest
import com.issueTracker.dtos.request.SignupRequest
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
import org.slf4j.LoggerFactory

@Suppress("LongMethod")
fun Route.configureUserRoutes() {
    val logger = LoggerFactory.getLogger("user_routes")

    route("/api/v1/user") {
        post("/signup") {
            val service = call.koinScope.get<UserService>()
            val request = call.receive<SignupRequest>()
            service
                .signup(request)
                .onSuccess {
                    call.respond(it)
                }
                .onFailure {
                    logger.error(it.toString())
                    call.respond(HttpStatusCode.BadRequest, it.message.toString())
                }
        }

        post("login") {
            val service = call.koinScope.get<UserService>()
            val request = call.receive<LoginRequest>()
            service
                .login(request)
                .onSuccess {
                    call.respond(it)
                }
                .onFailure {
                    logger.error(it.toString())
                    call.respond(HttpStatusCode.BadRequest, it.message.toString())
                }
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
