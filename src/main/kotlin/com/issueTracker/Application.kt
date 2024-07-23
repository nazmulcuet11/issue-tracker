package com.issueTracker

import com.issueTracker.routes.configureRoutes
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRoutes()
}
