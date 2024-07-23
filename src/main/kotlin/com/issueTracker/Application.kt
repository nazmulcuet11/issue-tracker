package com.issueTracker

import io.ktor.server.application.*
import com.issueTracker.plugins.configureRouting

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
}
