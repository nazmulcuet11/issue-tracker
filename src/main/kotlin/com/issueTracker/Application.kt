package com.issueTracker

import com.issueTracker.di.issueTrackerModule
import com.issueTracker.plugins.configureDI
import com.issueTracker.plugins.configureRouting
import com.issueTracker.plugins.contentNegotiation
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
    contentNegotiation()
    configureDI(listOf(issueTrackerModule))
}
