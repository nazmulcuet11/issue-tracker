package com.issueTracker.auth

import com.issueTracker.auth.plugins.configureAuthentication
import com.issueTracker.auth.plugins.configureDI
import com.issueTracker.auth.plugins.configureDatabase
import com.issueTracker.auth.plugins.configureLogging
import com.issueTracker.auth.plugins.configureRouting
import com.issueTracker.auth.plugins.configureSerialization
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
