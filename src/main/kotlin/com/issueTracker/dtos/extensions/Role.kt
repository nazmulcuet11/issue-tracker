package com.issueTracker.dtos.extensions

import com.issueTracker.dtos.responses.RoleResponse
import com.issueTracker.entities.Role

fun Role.toDto(): RoleResponse {
    return RoleResponse(
        this.id,
        this.name,
        this.description
    )
}
