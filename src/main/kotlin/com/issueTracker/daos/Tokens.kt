package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object Tokens: Table("tokens") {
    private const val MAX_TOKEN_LENGTH = 1024
    val value = varchar("value", MAX_TOKEN_LENGTH)
}
