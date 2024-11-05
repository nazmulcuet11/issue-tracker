package dev.nazmul.issueTracker.authService.mappers.entityToModels

import dev.nazmul.issueTracker.authService.entities.UserEntity
import dev.nazmul.issueTracker.authService.models.User

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
