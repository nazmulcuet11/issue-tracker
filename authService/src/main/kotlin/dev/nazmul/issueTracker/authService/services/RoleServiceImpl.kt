package dev.nazmul.issueTracker.authService.services

import dev.nazmul.issueTracker.authService.dtos.requests.CreateRoleRequest
import dev.nazmul.issueTracker.authService.models.Role
import dev.nazmul.issueTracker.authService.repositories.interfaces.RoleRepository
import dev.nazmul.issueTracker.authService.services.interfaces.RoleService

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
