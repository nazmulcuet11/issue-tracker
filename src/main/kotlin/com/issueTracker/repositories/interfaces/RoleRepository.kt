package com.issueTracker.repositories.interfaces

import com.issueTracker.entities.Role

interface RoleRepository {
    suspend fun getRolesByUserId(userId: Int): List<Role>
}
