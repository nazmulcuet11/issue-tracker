package com.issueTracker.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        configureOrderRoutes()
    }
}