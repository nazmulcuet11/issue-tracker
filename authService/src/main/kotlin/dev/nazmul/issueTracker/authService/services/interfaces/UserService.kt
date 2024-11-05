package dev.nazmul.issueTracker.authService.services.interfaces

import dev.nazmul.issueTracker.authService.dtos.requests.AssignRoleRequest
import dev.nazmul.issueTracker.authService.dtos.requests.LoginRequest
import dev.nazmul.issueTracker.authService.dtos.requests.SignupRequest
import dev.nazmul.issueTracker.authService.dtos.requests.TokenRefreshRequest
import dev.nazmul.issueTracker.authService.dtos.responses.AuthResponse
import dev.nazmul.issueTracker.authService.dtos.responses.SignupResponse
import dev.nazmul.issueTracker.authService.models.User

interface UserService {
    suspend  fun getUsers(offset: Long, limit: Int): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun signup(request: SignupRequest): Result<SignupResponse>
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun logout(userId: Int, token: String)
    suspend fun logoutAll(userId: Int)
    suspend fun tokenRefresh(request: TokenRefreshRequest): Result<AuthResponse>
    suspend fun assignRole(request: AssignRoleRequest)
}
