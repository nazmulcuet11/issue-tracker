package com.issueTracker.di

import com.issueTracker.constants.REQUEST_SCOPE_NAME
import com.issueTracker.repositories.IssueRepositoryImpl
import com.issueTracker.repositories.UserRepositoryImpl
import com.issueTracker.repositories.interfaces.IssueRepository
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.services.IssueServiceImpl
import com.issueTracker.services.JwtServiceImpl
import com.issueTracker.services.UserServiceImpl
import com.issueTracker.services.interfaces.IssueService
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.issueTrackerModule() = module {
    scope(named(REQUEST_SCOPE_NAME)) {
        scoped<IssueRepository> {
            IssueRepositoryImpl()
        }

        scoped<IssueService> {
            IssueServiceImpl(get())
        }
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }

    single<UserService> {
        UserServiceImpl(get())
    }

    single<JwtService> {
        JwtServiceImpl(environment.config, get())
    }
}
