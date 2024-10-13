package com.issueTracker.services

import com.issueTracker.dtos.requests.CreateRoleRequest
import com.issueTracker.models.Role
import com.issueTracker.repositories.interfaces.RoleRepository
import com.issueTracker.services.interfaces.RoleService

class RoleServiceImpl(
    private val repository: RoleRepository
): RoleService {
    override suspend fun createRole(request: CreateRoleRequest): Role {
        return repository.create(
            request.name,
            request.description ?: ""
        )
    }
}
