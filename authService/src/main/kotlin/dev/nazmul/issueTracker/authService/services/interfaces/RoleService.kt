package dev.nazmul.issueTracker.authService.services.interfaces

import dev.nazmul.issueTracker.authService.dtos.requests.CreateRoleRequest
import dev.nazmul.issueTracker.authService.models.Role

interface RoleService {
    suspend  fun getRoles(offset: Long, limit: Int): List<Role>
    suspend fun createRole(request: CreateRoleRequest): Role?
}
