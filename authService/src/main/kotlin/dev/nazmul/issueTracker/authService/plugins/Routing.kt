package dev.nazmul.issueTracker.authService.plugins

import dev.nazmul.issueTracker.authService.routes.configureRoleRoutes
import dev.nazmul.issueTracker.authService.routes.configureUserRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        configureUserRoutes()
        configureRoleRoutes()
    }
}
