package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object Roles: Table("roles") {
    private const val NAME_MAX_LENGTH = 50
    private const val DESCRIPTION_MAX_LENGTH = 255

    val id = integer("id").autoIncrement()
    val name = varchar("name", NAME_MAX_LENGTH)
    val description = varchar("description", DESCRIPTION_MAX_LENGTH).nullable()

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
