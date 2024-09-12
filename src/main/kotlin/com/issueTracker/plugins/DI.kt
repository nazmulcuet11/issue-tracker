package com.issueTracker.plugins

import com.issueTracker.di.KoinScope
import com.issueTracker.di.issueTrackerModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(
            listOf(
                issueTrackerModule(),
            )
        )
    }
    install(KoinScope)
}


