package com.issueTracker.services.interfaces

import com.issueTracker.dtos.requests.CreateRoleRequest
import com.issueTracker.models.Role

interface RoleService {
    suspend fun createRole(request: CreateRoleRequest): Role?
}
