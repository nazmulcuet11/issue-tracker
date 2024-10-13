package com.issueTracker.services.interfaces

import com.issueTracker.dtos.requests.CreateRoleRequest
import com.issueTracker.models.Role

interface RoleService {
    suspend  fun getRoles(offset: Long, limit: Int): List<Role>
    suspend fun createRole(request: CreateRoleRequest): Role?
}
