package com.issueTracker.daos

import org.jetbrains.exposed.sql.Table

object RolePermissions: Table("role_permissions") {
    private const val PERMISSION_NAME_MAX_LENGTH = 50

    val id = integer("id").autoIncrement()
    val roleId = integer("role_id") references(Roles.id)
    val permission = varchar("name", PERMISSION_NAME_MAX_LENGTH)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}