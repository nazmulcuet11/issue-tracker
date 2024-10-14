package com.issueTracker.auth.mappers.entityToModels

import com.issueTracker.auth.entities.UserEntity
import com.issueTracker.auth.models.User

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
