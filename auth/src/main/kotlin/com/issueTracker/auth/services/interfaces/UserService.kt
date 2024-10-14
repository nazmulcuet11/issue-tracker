package com.issueTracker.auth.services.interfaces

import com.issueTracker.auth.dtos.requests.AssignRoleRequest
import com.issueTracker.auth.dtos.requests.LoginRequest
import com.issueTracker.auth.dtos.requests.SignupRequest
import com.issueTracker.auth.dtos.requests.TokenRefreshRequest
import com.issueTracker.auth.dtos.responses.AuthResponse
import com.issueTracker.auth.dtos.responses.SignupResponse
import com.issueTracker.auth.models.User

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
