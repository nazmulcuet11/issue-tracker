package com.issueTracker.repositories.interfaces

import com.issueTracker.entities.User

interface UserRepository: EntityRepository<User> {
    suspend fun selectByEmail(email: String) : User?
}
