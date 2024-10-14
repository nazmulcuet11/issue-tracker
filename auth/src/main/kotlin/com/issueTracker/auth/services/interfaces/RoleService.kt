package com.issueTracker.auth.services.interfaces

import com.issueTracker.auth.dtos.requests.CreateRoleRequest
import com.issueTracker.auth.models.Role

interface RoleService {
    suspend  fun getRoles(offset: Long, limit: Int): List<Role>
    suspend fun createRole(request: CreateRoleRequest): Role?
}
