package com.issueTracker.repositories

import com.issueTracker.daos.Issues
import com.issueTracker.entities.Issue
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.IIssueRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.util.*

class IssueRepository: IIssueRepository {
    override suspend fun selectAll(): List<Issue> = dbQuery {
        Issues.selectAll().map(::resultRowToIssue)
    }

    override suspend fun selectById(id: Int): Issue? = dbQuery {
        Issues
            .select(Issues.id eq id)
            .singleOrNull()
            ?.let {
                resultRowToIssue(it)
            }
    }

    override suspend fun insert(entity: Issue): Issue? = dbQuery {
        val query = Issues.insert {
            it[Issues.title] = entity.title
            it[Issues.description] = entity.description
            it[Issues.createdAt] = entity.createdAt.time
        }
        query.resultedValues?.singleOrNull()?.let { resultRowToIssue(it) }
    }

    override suspend fun update(entity: Issue): Boolean = dbQuery {
        Issues.update({ Issues.id eq entity.id }) {
            it[Issues.title] = entity.title
            it[Issues.description] = entity.description
            it[Issues.createdAt] = entity.createdAt.time
        } > 0

    }

    override suspend fun delete(entity: Issue): Boolean {
        return deleteById(entity.id)
    }

    override suspend fun deleteById(id: Int): Boolean = dbQuery {
        Issues.deleteWhere { Issues.id eq id } > 0
    }

    private fun resultRowToIssue(row: ResultRow): Issue {
        return Issue(
            id = row[Issues.id],
            title = row[Issues.title],
            description = row[Issues.description],
            createdAt = Date(row[Issues.createdAt]),
        )
    }
}