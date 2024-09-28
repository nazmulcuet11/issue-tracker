package com.issueTracker.repositories

import com.issueTracker.daos.Roles
import com.issueTracker.daos.Users
import com.issueTracker.entities.Role
import com.issueTracker.entities.User
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.UserRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class UserRepositoryImpl : UserRepository {
    override suspend fun selectAll(): List<User> = dbQuery {
        Users
            .leftJoin(Roles)
            .selectAll().map(::resultRowToUser)
    }

    override suspend fun selectById(id: Int): User? = dbQuery {
        Users
            .leftJoin(Roles)
            .select(Users.id eq id)
            .singleOrNull()
            ?.let { resultRowToUser(it) }
    }

    override suspend fun selectByEmail(email: String): User? = dbQuery {
        Users
            .leftJoin(Roles)
            .select(Users.email eq email)
            .singleOrNull()
            ?.let { resultRowToUser(it) }
    }

    override suspend fun insert(entity: User): User? = dbQuery {
        val query = Users.insert {
            it[email] = entity.email
            it[firstName] = entity.firstName
            it[lastName] = entity.lastName
            it[passwordHash] = entity.passwordHash
        }
        query.resultedValues?.singleOrNull()?.let { resultRowToUser(it) }
    }

    override suspend fun update(entity: User): Boolean = dbQuery {
        Users.update({ Users.id eq entity.id }) {
            it[email] = entity.email
            it[firstName] = entity.firstName
            it[lastName] = entity.lastName
            it[passwordHash] = entity.passwordHash
        } > 0
    }

    override suspend fun delete(entity: User): Boolean {
        return deleteById(entity.id)
    }

    override suspend fun deleteById(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }

    private fun resultRowToUser(row: ResultRow): User {

        return User(
            id = row[Users.id],
            firstName = row[Users.firstName],
            lastName = row[Users.lastName],
            email = row[Users.email],
            passwordHash = row[Users.passwordHash],
            role = resultRowToRole(row)
        )
    }

    private fun resultRowToRole(row: ResultRow): Role? {
        return if (row.hasValue(Roles.id) && row[Roles.id] != null) {
            Role(
                id = row[Roles.id],
                name = row[Roles.name],
                description = row[Roles.description]
            )
        } else null
    }
}
