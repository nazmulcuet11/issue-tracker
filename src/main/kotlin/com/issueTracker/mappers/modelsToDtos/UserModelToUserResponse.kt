package com.issueTracker.mappers.modelsToDtos

import com.issueTracker.dtos.responses.UserResponse
import com.issueTracker.models.User

fun User.toDto(): UserResponse {
    return UserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        role = this.role.toDto(),
    )
}