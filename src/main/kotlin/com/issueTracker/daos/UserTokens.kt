package com.issueTracker.daos

import com.issueTracker.daos.RolePermissions.autoIncrement
import org.jetbrains.exposed.sql.Table

object UserTokens: Table("user_tokens") {
    private const val MAX_TOKEN_LENGTH = 1024

    val id = integer("id").autoIncrement()
    val userId = integer("user_id") references(Users.id)
    val refreshToken = varchar("refresh_token", MAX_TOKEN_LENGTH)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(Users.id)
}
