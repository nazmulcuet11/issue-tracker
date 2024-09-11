package com.issueTracker.di

import com.issueTracker.constants.REQUEST_SCOPE_NAME
import com.issueTracker.repositories.IssueRepositoryImpl
import com.issueTracker.repositories.interfaces.IssueRepository
import com.issueTracker.services.IssueServiceImpl
import com.issueTracker.services.interfaces.IssueService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val issueTrackerModule = module {
    scope(named(REQUEST_SCOPE_NAME)) {
        scoped<IssueRepository> {
            IssueRepositoryImpl()
        }

        scoped<IssueService> {
            IssueServiceImpl(get())
        }
    }
}