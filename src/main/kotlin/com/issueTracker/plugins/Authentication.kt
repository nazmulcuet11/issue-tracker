package com.issueTracker.plugins

import com.issueTracker.services.JwtConfig
import com.issueTracker.services.interfaces.JwtService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val config: JwtConfig by inject()
    install(Authentication) {
        jwt {
            verifier(config.verifier)
            realm = config.realm
            validate { jwtCredential ->
                val service: JwtService by inject()
                service.validate(jwtCredential)
            }
        }
    }
}
