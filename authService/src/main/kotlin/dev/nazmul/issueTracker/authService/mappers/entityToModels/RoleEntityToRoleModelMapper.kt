package dev.nazmul.issueTracker.authService.mappers.entityToModels

import dev.nazmul.issueTracker.authService.entities.RoleEntity
import dev.nazmul.issueTracker.authService.models.Role

fun RoleEntity.toModel(): Role {
    return Role(
        this.id.value,
        this.name,
        this.description
    )
}
