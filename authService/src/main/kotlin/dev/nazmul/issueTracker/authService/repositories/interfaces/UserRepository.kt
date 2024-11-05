package dev.nazmul.issueTracker.authService.repositories.interfaces

import dev.nazmul.issueTracker.authService.models.User

interface UserRepository {
    suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        passwordHash: String,
    ): User?

    suspend fun findAll(offset: Long, limit: Int): List<User>
    suspend fun findById(id: Int): User?
    suspend fun findByEmail(email: String): User?

    suspend fun assignRole(userId: Int, roleId: Int)
}
