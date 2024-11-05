package dev.nazmul.issueTracker.authService.di

import dev.nazmul.issueTracker.authService.constants.REQUEST_SCOPE_NAME
import dev.nazmul.issueTracker.authService.repositories.RoleRepositoryImpl
import dev.nazmul.issueTracker.authService.repositories.UserRepositoryImpl
import dev.nazmul.issueTracker.authService.repositories.UserTokenRepositoryImpl
import dev.nazmul.issueTracker.authService.repositories.interfaces.RoleRepository
import dev.nazmul.issueTracker.authService.repositories.interfaces.UserRepository
import dev.nazmul.issueTracker.authService.repositories.interfaces.UserTokenRepository
import dev.nazmul.issueTracker.authService.services.JwtServiceImpl
import dev.nazmul.issueTracker.authService.services.RoleServiceImpl
import dev.nazmul.issueTracker.authService.services.UserServiceImpl
import dev.nazmul.issueTracker.authService.services.interfaces.JwtService
import dev.nazmul.issueTracker.authService.services.interfaces.RoleService
import dev.nazmul.issueTracker.authService.services.interfaces.UserService
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
