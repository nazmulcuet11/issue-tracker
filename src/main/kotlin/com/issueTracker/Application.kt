package com.issueTracker

import com.issueTracker.plugins.configureAuthentication
import com.issueTracker.plugins.configureDI
import com.issueTracker.plugins.configureDatabase
import com.issueTracker.plugins.configureLogging
import com.issueTracker.plugins.configureRouting
import com.issueTracker.plugins.configureSerialization
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
