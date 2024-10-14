package com.issueTracker.auth.repositories.interfaces

import com.issueTracker.auth.models.Role

interface RoleRepository {
    suspend fun create(
        name: String,
        description: String
    ): Role

    suspend fun findAll(offset: Long, limit: Int): List<Role>
}
