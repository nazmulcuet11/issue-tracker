package com.issueTracker.repositories

import com.issueTracker.daos.Tokens
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.TokenRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class TokenRepositoryImpl : TokenRepository {
    override suspend fun insert(token: String): Boolean = dbQuery {
        val query = Tokens.insert {
            it[value] = token
        }
        query.resultedValues?.singleOrNull() != null
    }

    override suspend fun delete(token: String): Boolean = dbQuery {
        Tokens.deleteWhere { Tokens.value eq token } > 0
    }

    override suspend fun exists(token: String): Boolean = dbQuery {
        Tokens.select { Tokens.value eq token }.singleOrNull() != null
    }
}
