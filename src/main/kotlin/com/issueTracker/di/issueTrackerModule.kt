package com.issueTracker.di

import com.issueTracker.constants.REQUEST_SCOPE_NAME
import com.issueTracker.repositories.IssueRepository
import com.issueTracker.repositories.interfaces.IIssueRepository
import com.issueTracker.services.IssueService
import com.issueTracker.services.interfaces.IIssueService
import org.koin.core.qualifier.named
import org.koin.dsl.module

val issueTrackerModule = module {
    scope(named(REQUEST_SCOPE_NAME)) {
        scoped<IIssueRepository> {
            IssueRepository()
        }

        scoped<IIssueService> {
            IssueService(get())
        }
    }
}