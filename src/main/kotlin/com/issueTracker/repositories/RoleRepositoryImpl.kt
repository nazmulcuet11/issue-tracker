package com.issueTracker.repositories

import com.issueTracker.daos.Roles
import com.issueTracker.daos.Users
import com.issueTracker.entities.Role
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.RoleRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class RoleRepositoryImpl : RoleRepository {
    override suspend fun selectAll(): List<Role> = dbQuery {
        Roles
            .selectAll()
            .map(::resultRowToRole)
    }

    override suspend fun selectById(id: Int): Role? = dbQuery {
        Roles
            .select(Roles.id eq id)
            .singleOrNull()
            ?.let { resultRowToRole(it) }
    }

    override suspend fun insert(entity: Role): Role? = dbQuery {
        val query = Roles.insert {
            it[name] = entity.name
            it[description] = entity.description
        }
        query.resultedValues?.singleOrNull()?.let { resultRowToRole(it) }
    }

    override suspend fun update(entity: Role): Boolean = dbQuery {
        Roles.update({ Roles.id eq entity.id }) {
            it[name] = entity.name
            it[description] = entity.description
        } > 0
    }

    override suspend fun delete(entity: Role): Boolean {
        return deleteById(entity.id)
    }

    override suspend fun deleteById(id: Int): Boolean = dbQuery {
        Roles.deleteWhere { Users.id eq id } > 0
    }

    private fun resultRowToRole(row: ResultRow): Role {
        return Role(
            id = row[Roles.id],
            name = row[Roles.name],
            description = row[Roles.description]
        )
    }
}
