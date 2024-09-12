package com.issueTracker.dtos.extensions

import com.issueTracker.dtos.responses.UserResponse
import com.issueTracker.entities.User

fun User.toDto(): UserResponse {
    return UserResponse(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
    )
}
