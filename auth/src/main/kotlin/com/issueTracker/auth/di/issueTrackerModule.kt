package com.issueTracker.auth.di

import com.issueTracker.auth.constants.REQUEST_SCOPE_NAME
import com.issueTracker.auth.repositories.RoleRepositoryImpl
import com.issueTracker.auth.repositories.UserRepositoryImpl
import com.issueTracker.auth.repositories.UserTokenRepositoryImpl
import com.issueTracker.auth.repositories.interfaces.RoleRepository
import com.issueTracker.auth.repositories.interfaces.UserRepository
import com.issueTracker.auth.repositories.interfaces.UserTokenRepository
import com.issueTracker.auth.services.JwtServiceImpl
import com.issueTracker.auth.services.RoleServiceImpl
import com.issueTracker.auth.services.UserServiceImpl
import com.issueTracker.auth.services.interfaces.JwtService
import com.issueTracker.auth.services.interfaces.RoleService
import com.issueTracker.auth.services.interfaces.UserService
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.issueTrackerModule() = module {
    single<JwtService> {
        JwtServiceImpl(environment.config)
    }

    scope(named(REQUEST_SCOPE_NAME)) {
        scoped<UserRepository> {
            UserRepositoryImpl()
        }

        scoped<UserTokenRepository> {
            UserTokenRepositoryImpl()
        }

        scoped<RoleRepository> {
            RoleRepositoryImpl()
        }

        scoped<RoleService> {
            RoleServiceImpl(get())
        }

        scoped<UserService> {
            UserServiceImpl(get(), get(), get())
        }
    }
}
