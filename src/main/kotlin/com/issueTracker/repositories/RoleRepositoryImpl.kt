package com.issueTracker.repositories

import com.issueTracker.entities.RoleEntity
import com.issueTracker.mappers.entityToModels.toModel
import com.issueTracker.models.Role
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.RoleRepository

class RoleRepositoryImpl : RoleRepository {
    override suspend fun all(): List<Role> = dbQuery {
        RoleEntity.all().map { it.toModel() }
    }

    override suspend fun create(
        name: String,
        description: String
    ): Role = dbQuery {
        val entity = RoleEntity.new {
            this.name = name
            this.description = description
        }
        entity.toModel()
    }
}
