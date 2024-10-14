package com.issueTracker.auth.repositories

import com.issueTracker.auth.constants.Roles
import com.issueTracker.auth.entities.RoleEntity
import com.issueTracker.auth.entities.UserEntity
import com.issueTracker.auth.mappers.entityToModels.toModel
import com.issueTracker.auth.models.User
import com.issueTracker.auth.plugins.dbQuery
import com.issueTracker.auth.repositories.interfaces.UserRepository
import com.issueTracker.auth.tables.Users

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

    override suspend fun assignRole(userId: Int, roleId: Int) = dbQuery {
        val user = UserEntity.findById(userId) ?: throw Error("user not found for id: $userId")
        val role = RoleEntity.findById(roleId) ?: throw Error("role not found for id: $roleId")
        user.role = role
    }
}
