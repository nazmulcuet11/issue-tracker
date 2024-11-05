package dev.nazmul.issueTracker.authService.tables

import org.jetbrains.exposed.sql.Table

object UserTokens : Table("user_tokens") {
    private const val MAX_TOKEN_LENGTH = 1024

    val user = reference("user_id", Users)
    val refreshToken = varchar("refresh_token", MAX_TOKEN_LENGTH)
}
