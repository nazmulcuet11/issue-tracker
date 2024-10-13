package com.issueTracker.mappers.entityToModels

import com.issueTracker.entities.UserEntity
import com.issueTracker.models.User

fun UserEntity.toModel(): User {
    return User(
        this.id.value,
        this.firstName,
        this.lastName,
        this.email,
        this.passwordHash,
        this.role.toModel()
    )
}
