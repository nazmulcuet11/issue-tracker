package com.issueTracker.repositories

import com.issueTracker.daos.Issues
import com.issueTracker.entities.Issue
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.IIssueRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class IssueRepository: IIssueRepository {
    override suspend fun getAll(): List<Issue> = dbQuery{
        Issues.selectAll().map(::resultRowToIssue)
    }

    private fun resultRowToIssue(row: ResultRow): Issue {
        return Issue(
            id = row[Issues.id].toString(),
            title = row[Issues.title],
            description = row[Issues.description],
            createdAt = Date(row[Issues.createdAt]),
        )
    }
}