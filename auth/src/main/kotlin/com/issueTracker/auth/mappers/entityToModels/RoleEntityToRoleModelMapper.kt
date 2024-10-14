package com.issueTracker.auth.mappers.entityToModels

import com.issueTracker.auth.entities.RoleEntity
import com.issueTracker.auth.models.Role

fun RoleEntity.toModel(): Role {
    return Role(
        this.id.value,
        this.name,
        this.description
    )
}
