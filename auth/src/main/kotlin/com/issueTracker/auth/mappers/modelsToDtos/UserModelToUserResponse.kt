package com.issueTracker.auth.mappers.modelsToDtos

import com.issueTracker.auth.dtos.responses.UserResponse
import com.issueTracker.auth.models.User

fun User.toDto(): UserResponse {
    return UserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        role = this.role.toDto(),
    )
}
