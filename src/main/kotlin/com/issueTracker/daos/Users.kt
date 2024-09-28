package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    private const val NAME_EMAIL_MAX_LENGTH = 50
    private const val PASSWORD_MAX_LENGTH = 255

    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", NAME_EMAIL_MAX_LENGTH)
    val lastName = varchar("last_name", NAME_EMAIL_MAX_LENGTH)
    val email = varchar("email", NAME_EMAIL_MAX_LENGTH).uniqueIndex()
    val passwordHash = varchar("password_hash", PASSWORD_MAX_LENGTH)
    val roleId = integer("role_id").references(Roles.id).nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
