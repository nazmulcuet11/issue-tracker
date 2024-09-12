package com.issueTracker.services


import com.issueTracker.entities.User
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.services.interfaces.UserService
import org.springframework.security.crypto.bcrypt.BCrypt

class UserServiceImpl(
    private val repository: UserRepository
): UserService {
    override suspend fun getAllUsers(): List<User> {
        return repository.selectAll()
    }

    override suspend fun getUserById(id: Int): User? {
        return repository.selectById(id)
    }

    override suspend fun getUserByEmail(email: String): User? {
        return repository.selectByEmail(email)
    }

    override suspend fun createUser(firstName: String, lastName: String, email: String, password: String): User? {
        val user = User(
            id = 0,
            firstName = firstName,
            lastName = lastName,
            email = email,
            passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
        )
        return repository.insert(user)
    }
}

