package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object Issues: Table("issues") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 1024)
    val description = text("description")
    val createdAt = long("created_at")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}