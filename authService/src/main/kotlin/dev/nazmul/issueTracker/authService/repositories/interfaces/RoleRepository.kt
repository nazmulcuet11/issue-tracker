package dev.nazmul.issueTracker.authService.repositories.interfaces

import dev.nazmul.issueTracker.authService.models.Role

interface RoleRepository {
    suspend fun create(
        name: String,
        description: String
    ): Role

    suspend fun findAll(offset: Long, limit: Int): List<Role>
}
