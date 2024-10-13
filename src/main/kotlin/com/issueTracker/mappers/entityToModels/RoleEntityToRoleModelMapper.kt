package com.issueTracker.mappers.entityToModels

import com.issueTracker.entities.RoleEntity
import com.issueTracker.models.Role

fun RoleEntity.toModel(): Role {
    return Role(
        this.id.value,
        this.name,
        this.description
    )
}
