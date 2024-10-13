package com.issueTracker.di

import com.issueTracker.constants.REQUEST_SCOPE_NAME
//import com.issueTracker.repositories.IssueRepositoryImpl
import com.issueTracker.repositories.RoleRepositoryImpl
import com.issueTracker.repositories.UserRepositoryImpl
import com.issueTracker.repositories.UserTokenRepositoryImpl
//import com.issueTracker.repositories.interfaces.IssueRepository
import com.issueTracker.repositories.interfaces.RoleRepository
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.repositories.interfaces.UserTokenRepository
//import com.issueTracker.services.IssueServiceImpl
import com.issueTracker.services.JwtServiceImpl
import com.issueTracker.services.RoleServiceImpl
import com.issueTracker.services.UserServiceImpl
//import com.issueTracker.services.interfaces.IssueService
import com.issueTracker.services.interfaces.JwtService
import com.issueTracker.services.interfaces.RoleService
import com.issueTracker.services.interfaces.UserService
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.issueTrackerModule() = module {
    single<JwtService> {
        JwtServiceImpl(environment.config)
    }

    scope(named(REQUEST_SCOPE_NAME)) {
//        scoped<IssueRepository> {
//            IssueRepositoryImpl()
//        }

//        scoped<IssueService> {
//            IssueServiceImpl(get())
//        }

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
