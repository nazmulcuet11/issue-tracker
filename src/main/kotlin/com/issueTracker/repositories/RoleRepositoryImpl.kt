package com.issueTracker.repositories

import com.issueTracker.daos.Roles
import com.issueTracker.daos.UserRoles
import com.issueTracker.entities.Role
import com.issueTracker.plugins.dbQuery
import com.issueTracker.repositories.interfaces.RoleRepository
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select

class RoleRepositoryImpl: RoleRepository {
    override suspend fun getRolesByUserId(userId: Int): List<Role> = dbQuery {
        UserRoles
            .join(
                Roles,
                JoinType.LEFT,
                onColumn = UserRoles.roleId,
                otherColumn = Roles.id
            )
            .slice(
                Roles.id,
                Roles.name,
                Roles.description
            )
            .select( UserRoles.userId eq userId )
            .map(::resultRowToRole)
    }

    private fun resultRowToRole(row: ResultRow): Role {
        return Role(
            id = row[Roles.id],
            name = row[Roles.name],
            description = row[Roles.description],
        )
    }
}
