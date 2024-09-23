package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object UserRoles: Table("user_roles") {
    val id = integer("id").autoIncrement()
    val userId = integer("user_id") references(Users.id)
    val roleId = integer("role_id") references(Roles.id)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}
