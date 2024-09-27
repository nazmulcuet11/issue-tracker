package com.issueTracker.services.interfaces

import com.issueTracker.dtos.requests.LoginRequest
import com.issueTracker.dtos.requests.SignupRequest
import com.issueTracker.dtos.requests.TokenRefreshRequest
import com.issueTracker.dtos.responses.AuthResponse
import com.issueTracker.dtos.responses.SignupResponse
import com.issueTracker.entities.User

interface UserService {
    suspend  fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun signup(request: SignupRequest): Result<SignupResponse>
    suspend fun login(request: LoginRequest): Result<AuthResponse>
    suspend fun logout(userId: Int, token: String)
    suspend fun logoutAll(userId: Int)
    suspend fun tokenRefresh(request: TokenRefreshRequest): Result<AuthResponse>
}
