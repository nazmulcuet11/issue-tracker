package com.issueTracker.auth.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Roles: IntIdTable("roles") {
    private const val NAME_MAX_LENGTH = 50
    private const val DESCRIPTION_MAX_LENGTH = 255

    val name = varchar("name", NAME_MAX_LENGTH)
    val description = varchar("description", DESCRIPTION_MAX_LENGTH)
}
