package dev.nazmul.issueTracker.authService.mappers.modelsToDtos

import dev.nazmul.issueTracker.authService.dtos.responses.UserResponse
import dev.nazmul.issueTracker.authService.models.User

fun User.toDto(): UserResponse {
    return UserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        role = this.role.toDto(),
    )
}
