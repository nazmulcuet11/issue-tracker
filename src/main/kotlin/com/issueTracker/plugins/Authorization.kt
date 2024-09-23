package com.issueTracker.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.createRouteScopedPlugin
import io.ktor.server.auth.AuthenticationChecked
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond

val AuthorizationPlugin = createRouteScopedPlugin(
    name = "AuthorizationPlugin",
    createConfiguration = ::AuthorizationConfig
) {
    val roles = pluginConfig.roles
    pluginConfig.apply {
        on(AuthenticationChecked) { call ->
            val tokenRole = getRoleFromToken(call)
            val authorized = roles.contains(tokenRole)
            if (authorized.not()) {
                call.respond(HttpStatusCode.Forbidden)
            }
        }
    }
}

class AuthorizationConfig {
    var roles = setOf<String>()
}

private fun getRoleFromToken(call: ApplicationCall): String? = call
    .principal<JWTPrincipal>()
    ?.payload
    ?.getClaim("role")
    ?.asString()
