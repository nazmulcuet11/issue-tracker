package com.issueTracker.auth.plugins

import com.issueTracker.auth.routes.configureRoleRoutes
import com.issueTracker.auth.routes.configureUserRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        configureUserRoutes()
        configureRoleRoutes()
    }
}
