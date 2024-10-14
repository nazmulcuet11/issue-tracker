package com.issueTracker.auth.services

import com.issueTracker.auth.dtos.requests.CreateRoleRequest
import com.issueTracker.auth.models.Role
import com.issueTracker.auth.repositories.interfaces.RoleRepository
import com.issueTracker.auth.services.interfaces.RoleService

class RoleServiceImpl(
    private val repository: RoleRepository
) : RoleService {
    override suspend fun getRoles(offset: Long, limit: Int): List<Role> {
        return repository.findAll(offset, limit)
    }

    override suspend fun createRole(request: CreateRoleRequest): Role {
        return repository.create(
            request.name,
            request.description ?: ""
        )
    }
}
