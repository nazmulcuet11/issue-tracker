package dev.nazmul.issueTracker.authService

import dev.nazmul.issueTracker.authService.plugins.configureAuthentication
import dev.nazmul.issueTracker.authService.plugins.configureDI
import dev.nazmul.issueTracker.authService.plugins.configureDatabase
import dev.nazmul.issueTracker.authService.plugins.configureLogging
import dev.nazmul.issueTracker.authService.plugins.configureRouting
import dev.nazmul.issueTracker.authService.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureLogging()
    configureDI()
    configureAuthentication()
    configureDatabase()
    configureSerialization()
    configureRouting()
}
