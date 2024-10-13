package com.issueTracker.entities

import com.issueTracker.tables.Users
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(
    id: EntityID<Int>,
): IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var firstName by Users.firstName
    var lastName by Users.lastName
    var email by Users.email
    var passwordHash by Users.passwordHash
    var role by RoleEntity referencedOn Users.role
}
