package com.issueTracker.di

import com.issueTracker.constants.REQUEST_SCOPE_NAME
import com.issueTracker.repositories.IssueRepositoryImpl
import com.issueTracker.repositories.TokenRepositoryImpl
import com.issueTracker.repositories.UserRepositoryImpl
import com.issueTracker.repositories.interfaces.IssueRepository
import com.issueTracker.repositories.interfaces.TokenRepository
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.services.IssueServiceImpl
import com.issueTracker.services.JwtConfig
import com.issueTracker.services.JwtServiceImpl
import com.issueTracker.services.UserServiceImpl
import com.issueTracker.services.interfaces.IssueService
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.UserService
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.issueTrackerModule() = module {
    single<JwtConfig> {
        JwtConfig(environment.config)
    }

    scope(named(REQUEST_SCOPE_NAME)) {
        scoped<IssueRepository> {
            IssueRepositoryImpl()
        }

        scoped<IssueService> {
            IssueServiceImpl(get())
        }

        scoped<UserRepository> {
            UserRepositoryImpl()
        }

        scoped<JwtService> {
            JwtServiceImpl(get())
        }

        scoped<TokenRepository> {
            TokenRepositoryImpl()
        }

        scoped<UserService> {
            UserServiceImpl(get(), get(), get())
        }
    }
}
