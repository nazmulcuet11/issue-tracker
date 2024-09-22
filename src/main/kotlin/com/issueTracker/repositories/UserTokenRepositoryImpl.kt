package com.issueTracker.repositories

import com.issueTracker.daos.UserTokens
import com.issueTracker.daos.UserTokens.refreshToken
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.UserTokenRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserTokenRepositoryImpl : UserTokenRepository {
    override suspend fun insert(token: String, userId: Int): Boolean = dbQuery {
        val query = UserTokens.insert {
            it[UserTokens.userId] = userId
            it[refreshToken] = token
        }
        query.resultedValues?.singleOrNull() != null
    }

    override suspend fun deleteAllToken(userId: Int): Boolean = dbQuery {
        UserTokens.deleteWhere { UserTokens.userId eq userId } > 0
    }

    override suspend fun delete(token: String, userId: Int): Boolean = dbQuery {
        UserTokens.deleteWhere {
            (UserTokens.userId eq userId) and (refreshToken eq token)
        } > 0
    }

    override suspend fun exists(token: String, userId: Int): Boolean = dbQuery {
        UserTokens.select {
            (UserTokens.userId eq userId) and (refreshToken eq token)
        }.singleOrNull() != null
    }
}
