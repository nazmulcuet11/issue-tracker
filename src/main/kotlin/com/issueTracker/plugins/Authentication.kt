package com.issueTracker.plugins

import com.issueTracker.services.interfaces.JwtService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import org.koin.ktor.ext.inject

fun Application.configureAuthentication() {
    val service: JwtService by inject()
    install(Authentication) {
        jwt {
            verifier(service.verifier)
            realm = service.realm
            validate { jwtCredential ->
                service.validate(jwtCredential)
            }
        }
    }
}
