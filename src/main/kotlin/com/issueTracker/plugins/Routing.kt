package com.issueTracker.plugins

import com.issueTracker.routes.configureOrderRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        configureOrderRoutes()
    }
}