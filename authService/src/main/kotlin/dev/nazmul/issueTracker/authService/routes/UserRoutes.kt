package dev.nazmul.issueTracker.authService.routes

import dev.nazmul.issueTracker.authService.di.koinScope
import dev.nazmul.issueTracker.authService.dtos.requests.AssignRoleRequest
import dev.nazmul.issueTracker.authService.dtos.requests.LoginRequest
import dev.nazmul.issueTracker.authService.dtos.requests.LogoutRequest
import dev.nazmul.issueTracker.authService.dtos.requests.SignupRequest
import dev.nazmul.issueTracker.authService.dtos.requests.TokenRefreshRequest
import dev.nazmul.issueTracker.authService.dtos.requests.UserListRequest
import dev.nazmul.issueTracker.authService.mappers.modelsToDtos.toDto
import dev.nazmul.issueTracker.authService.routes.extensions.authorized
import dev.nazmul.issueTracker.authService.services.interfaces.UserService
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

        post("/login") {
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

        post("/token-refresh") {
            val service = call.koinScope.get<UserService>()
            val request = call.receive<TokenRefreshRequest>()
            service
                .tokenRefresh(request)
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

        authenticate {
            post("/logout") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.payload?.getClaim("id")?.asInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val request = call.receive<LogoutRequest>()
                val service = call.koinScope.get<UserService>()
                service.logout(id, request.refreshToken)
                call.respond(HttpStatusCode.OK)
            }
        }

        authenticate {
            post("/logout-all") {
                val principal = call.principal<JWTPrincipal>()
                val id = principal?.payload?.getClaim("id")?.asInt()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
                val service = call.koinScope.get<UserService>()
                service.logoutAll(id)
                call.respond(HttpStatusCode.OK)
            }
        }

        authenticate {
            authorized("admin") {
                post("/list") {
                    val service = call.koinScope.get<UserService>()
                    val request = call.receive<UserListRequest>()
                    val users = service.getUsers(request.offset, request.limit)
                    val usersDto = users.map { it.toDto() }
                    call.respond(usersDto)
                }

                post("/assign-role") {
                    val request = call.receive<AssignRoleRequest>()
                    val service = call.koinScope.get<UserService>()
                    service.assignRole(request)
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}
