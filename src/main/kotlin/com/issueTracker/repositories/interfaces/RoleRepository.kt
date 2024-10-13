package com.issueTracker.repositories.interfaces

import com.issueTracker.models.Role

interface RoleRepository {
    suspend fun create(
        name: String,
        description: String
    ): Role

    suspend fun findAll(offset: Long, limit: Int): List<Role>
}
