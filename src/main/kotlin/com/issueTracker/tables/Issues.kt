//package com.issueTracker.tables
//
//import org.jetbrains.exposed.sql.Table
//
//object Issues: Table("issues") {
//    private const val TITLE_LENGTH = 1024
//
//    val id = integer("id").autoIncrement()
//    val title = varchar("title", TITLE_LENGTH)
//    val description = text("description").nullable()
//    val createdAt = long("created_at")
//
//    override val primaryKey: PrimaryKey
//        get() = PrimaryKey(id)
//}
