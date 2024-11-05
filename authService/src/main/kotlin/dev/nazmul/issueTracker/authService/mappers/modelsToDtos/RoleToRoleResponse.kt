package dev.nazmul.issueTracker.authService.mappers.modelsToDtos

import dev.nazmul.issueTracker.authService.dtos.responses.RoleResponse
import dev.nazmul.issueTracker.authService.models.Role

fun Role.toDto(): RoleResponse {
    return RoleResponse(
        this.id,
        this.name,
        this.description
    )
}
