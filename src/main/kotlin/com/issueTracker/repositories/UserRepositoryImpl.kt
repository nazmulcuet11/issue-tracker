package com.issueTracker.repositories

import com.issueTracker.constants.Roles
import com.issueTracker.entities.RoleEntity
import com.issueTracker.entities.UserEntity
import com.issueTracker.mappers.entityToModels.toModel
import com.issueTracker.models.User
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.UserRepository
import com.issueTracker.tables.Users

class UserRepositoryImpl : UserRepository {
    override suspend fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        passwordHash: String
    ): User = dbQuery {
        val roleEntity = RoleEntity.findById(Roles.USER_ROLE_ID)!!
        val userEntity = UserEntity.new {
            this.firstName = firstName
            this.lastName = lastName
            this.email = email
            this.passwordHash = passwordHash
            this.role = roleEntity
        }
        userEntity.toModel()
    }

    override suspend fun findAll(offset: Long, limit: Int): List<User> = dbQuery {
        UserEntity
            .all()
            .limit(limit, offset)
            .map { it.toModel() }
    }

    override suspend fun findById(id: Int): User? = dbQuery {
        UserEntity
            .find { Users.id eq id }
            .singleOrNull()
            ?.toModel()
    }

    override suspend fun findByEmail(email: String): User? = dbQuery {
        UserEntity
            .find { Users.email eq email }
            .singleOrNull()
            ?.toModel()
    }
}
