package dev.nazmul.issueTracker.authService.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.nazmul.issueTracker.authService.constants.MAXIMUM_DB_CONNECTION_PULL_SIZE
import dev.nazmul.issueTracker.authService.entities.RoleEntity
import dev.nazmul.issueTracker.authService.entities.UserEntity
import dev.nazmul.issueTracker.authService.tables.Roles
import dev.nazmul.issueTracker.authService.tables.UserTokens
import dev.nazmul.issueTracker.authService.tables.Users
import io.ktor.server.application.Application
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.bcrypt.BCrypt

suspend fun <T> dbQuery(block: suspend () -> T): T {
    return newSuspendedTransaction(Dispatchers.IO) { block() }
}

private fun provideDataSource(url: String, driverClass: String): HikariDataSource {
    val hikariConfig = HikariConfig().apply {
        driverClassName = driverClass
        jdbcUrl = url
        maximumPoolSize = MAXIMUM_DB_CONNECTION_PULL_SIZE
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    return HikariDataSource(hikariConfig)
}

fun Application.configureDatabase() {
    val driverClass = environment.config.property("storage.driverClassName").getString()
    val jdbcUrl = environment.config.property("storage.jdbcURL").getString()
    val db = Database.connect(provideDataSource(jdbcUrl, driverClass))
    transaction(db) {
        SchemaUtils.create(Users)
        SchemaUtils.create(UserTokens)
        SchemaUtils.create(Roles)

        initializeDatabase()
    }
}

private fun initializeDatabase() {
    if (RoleEntity.findById(dev.nazmul.issueTracker.authService.constants.Roles.USER_ROLE_ID) == null) {
        RoleEntity.new {
            name = "user"
            description = "default role"
        }
    }

    if (RoleEntity.findById(dev.nazmul.issueTracker.authService.constants.Roles.ADMIN_ROLE_ID) == null) {
        RoleEntity.new {
            name = "admin"
            description = "admin role, permitted to do everything"
        }
    }

    if (UserEntity.findById(dev.nazmul.issueTracker.authService.constants.Users.ADMIN_USER_ID) == null) {
        UserEntity.new {
            firstName = "admin"
            lastName = "admin"
            email = "admin@domain.com"
            passwordHash = BCrypt.hashpw("admin", BCrypt.gensalt())
            role = RoleEntity.findById(dev.nazmul.issueTracker.authService.constants.Roles.ADMIN_ROLE_ID)!!
        }
    }
}
