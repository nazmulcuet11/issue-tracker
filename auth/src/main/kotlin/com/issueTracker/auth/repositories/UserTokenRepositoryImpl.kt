package com.issueTracker.auth.repositories

import com.issueTracker.auth.plugins.dbQuery
import com.issueTracker.auth.repositories.interfaces.UserTokenRepository
import com.issueTracker.auth.tables.UserTokens
import com.issueTracker.auth.tables.UserTokens.refreshToken
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserTokenRepositoryImpl : UserTokenRepository {
    override suspend fun insert(token: String, userId: Int): Boolean = dbQuery {
        val query = UserTokens.insert {
            it[user] = userId
            it[refreshToken] = token
        }
        query.resultedValues?.singleOrNull() != null
    }

    override suspend fun deleteAllToken(userId: Int): Boolean = dbQuery {
        UserTokens.deleteWhere { UserTokens.user eq userId } > 0
    }

    override suspend fun delete(token: String, userId: Int): Boolean = dbQuery {
        UserTokens.deleteWhere {
            (UserTokens.user eq userId) and (refreshToken eq token)
        } > 0
    }

    override suspend fun exists(token: String, userId: Int): Boolean = dbQuery {
        UserTokens.select {
            (UserTokens.user eq userId) and (refreshToken eq token)
        }.singleOrNull() != null
    }
}
