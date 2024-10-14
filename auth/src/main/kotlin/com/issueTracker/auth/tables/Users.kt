package com.issueTracker.auth.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable("users") {
    private const val NAME_EMAIL_MAX_LENGTH = 50
    private const val PASSWORD_MAX_LENGTH = 255

    val firstName = varchar("first_name", NAME_EMAIL_MAX_LENGTH)
    val lastName = varchar("last_name", NAME_EMAIL_MAX_LENGTH)
    val email = varchar("email", NAME_EMAIL_MAX_LENGTH).uniqueIndex()
    val passwordHash = varchar("password_hash", PASSWORD_MAX_LENGTH)

    val role = reference("role_id", Roles)
}
