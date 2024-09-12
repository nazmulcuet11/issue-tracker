package com.issueTracker.services.interfaces

import com.issueTracker.entities.User

interface UserService {
    suspend  fun getAllUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): User?
}
