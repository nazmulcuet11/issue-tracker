package com.issueTracker.auth.routes.extensions

import com.issueTracker.auth.plugins.AuthorizationPlugin
import io.ktor.server.application.install
import io.ktor.server.routing.Route

fun Route.authorized(
    vararg hasAnyRoles: String,
    build: Route.() -> Unit
) {
    install(AuthorizationPlugin) {
        roles = hasAnyRoles.toSet()
    }
    build()
}
