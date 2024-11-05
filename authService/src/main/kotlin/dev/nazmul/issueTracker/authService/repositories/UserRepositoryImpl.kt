package dev.nazmul.issueTracker.authService.repositories

import dev.nazmul.issueTracker.authService.constants.Roles
import dev.nazmul.issueTracker.authService.entities.RoleEntity
import dev.nazmul.issueTracker.authService.entities.UserEntity
import dev.nazmul.issueTracker.authService.mappers.entityToModels.toModel
import dev.nazmul.issueTracker.authService.models.User
import dev.nazmul.issueTracker.authService.plugins.dbQuery
import dev.nazmul.issueTracker.authService.repositories.interfaces.UserRepository
import dev.nazmul.issueTracker.authService.tables.Users

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
