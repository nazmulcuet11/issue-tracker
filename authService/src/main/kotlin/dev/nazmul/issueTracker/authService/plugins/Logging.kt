package dev.nazmul.issueTracker.authService.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging

fun Application.configureLogging() {
    install(CallLogging)
}
