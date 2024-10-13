package com.issueTracker.plugins

//import com.issueTracker.routes.configureIssueRoutes
import com.issueTracker.routes.configureRoleRoutes
import com.issueTracker.routes.configureUserRoutes
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
//        configureIssueRoutes()
        configureUserRoutes()
        configureRoleRoutes()
    }
}
