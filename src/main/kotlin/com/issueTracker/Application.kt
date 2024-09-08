package com.issueTracker

import com.issueTracker.plugins.*
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureLogging()
    configureDI()
    configureDatabase()
    configureSerialization()
    configureRouting()
}
