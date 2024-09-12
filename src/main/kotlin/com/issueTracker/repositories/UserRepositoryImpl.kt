package com.issueTracker.repositories

import com.issueTracker.entities.User
import com.issueTracker.repositories.interfaces.UserRepository

class UserRepositoryImpl: UserRepository {
    private var id = 0
    private val users = mutableListOf<User>()

    override suspend fun selectAll(): List<User> {
        return users
    }

    override suspend fun selectById(id: Int): User? {
        return users.find { it.id == id }
    }

    override suspend fun selectByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    override suspend fun insert(entity: User): User? {
        users.add(
            User(
                id = id,
                firstName = entity.firstName,
                lastName = entity.lastName,
                email = entity.email,
                passwordHash = entity.passwordHash
            )
        )
        id++
        return entity
    }

    override suspend fun update(entity: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(entity: User): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun deleteById(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}
