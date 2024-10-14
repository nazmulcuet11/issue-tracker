package com.issueTracker.auth.mappers.modelsToDtos

import com.issueTracker.auth.dtos.responses.RoleResponse
import com.issueTracker.auth.models.Role

fun Role.toDto(): RoleResponse {
    return RoleResponse(
        this.id,
        this.name,
        this.description
    )
}
