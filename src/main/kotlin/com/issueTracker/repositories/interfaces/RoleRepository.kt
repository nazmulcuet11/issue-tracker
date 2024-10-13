package com.issueTracker.repositories.interfaces

import com.issueTracker.models.Role

interface RoleRepository {
    suspend fun all(): List<Role>
    suspend fun create(
        name: String,
        description: String
    ): Role
}
