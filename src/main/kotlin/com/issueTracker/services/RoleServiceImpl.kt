package com.issueTracker.services

import com.issueTracker.dtos.requests.CreateRoleRequest
import com.issueTracker.entities.Role
import com.issueTracker.repositories.interfaces.RoleRepository
import com.issueTracker.services.interfaces.RoleService

class RoleServiceImpl(
    private val repository: RoleRepository
): RoleService {
    override suspend fun createRole(request: CreateRoleRequest): Role? {
        val role = Role(
            id = 0,
            name = request.name,
            description = request.description
        )
        return repository.insert(role)
    }
}
