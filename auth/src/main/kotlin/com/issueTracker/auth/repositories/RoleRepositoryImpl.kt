package com.issueTracker.auth.repositories

import com.issueTracker.auth.entities.RoleEntity
import com.issueTracker.auth.mappers.entityToModels.toModel
import com.issueTracker.auth.models.Role
import com.issueTracker.auth.plugins.dbQuery
import com.issueTracker.auth.repositories.interfaces.RoleRepository

class RoleRepositoryImpl : RoleRepository {
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

    override suspend fun findAll(offset: Long, limit: Int): List<Role> = dbQuery {
        RoleEntity
            .all()
            .limit(limit, offset)
            .map { it.toModel() }
    }
}
