package com.issueTracker.mappers.modelsToDtos

import com.issueTracker.dtos.responses.RoleResponse
import com.issueTracker.models.Role

fun Role.toDto(): RoleResponse {
    return RoleResponse(
        this.id,
        this.name,
        this.description
    )
}
