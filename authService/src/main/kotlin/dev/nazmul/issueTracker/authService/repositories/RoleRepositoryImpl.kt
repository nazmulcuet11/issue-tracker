package dev.nazmul.issueTracker.authService.repositories

import dev.nazmul.issueTracker.authService.entities.RoleEntity
import dev.nazmul.issueTracker.authService.mappers.entityToModels.toModel
import dev.nazmul.issueTracker.authService.models.Role
import dev.nazmul.issueTracker.authService.plugins.dbQuery
import dev.nazmul.issueTracker.authService.repositories.interfaces.RoleRepository

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
