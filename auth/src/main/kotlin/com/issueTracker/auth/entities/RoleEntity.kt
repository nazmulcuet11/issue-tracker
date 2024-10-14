package com.issueTracker.auth.entities

import com.issueTracker.auth.tables.Roles
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RoleEntity(
    id: EntityID<Int>,
): IntEntity(id) {
    companion object : IntEntityClass<RoleEntity>(Roles)

    var name by Roles.name
    var description by Roles.description
}
