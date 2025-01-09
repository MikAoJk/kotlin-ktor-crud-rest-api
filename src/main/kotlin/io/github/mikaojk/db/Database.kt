package io.github.mikaojk.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.mikaojk.EnvironmentVariables
import java.sql.Connection
import java.sql.ResultSet
import org.flywaydb.core.Flyway

class Database(private val environmentVariables: EnvironmentVariables) : DatabaseInterface {
    private val dataSource: HikariDataSource

    override val connection: Connection
        get() = dataSource.connection

    init {
        runFlywayMigrations()

        dataSource =
            HikariDataSource(
                HikariConfig().apply {
                    jdbcUrl =
                        "jdbc:postgresql://${environmentVariables.databaseHostUrl}:5432/postgres"
                    username = "postgres"
                    password = "postgres"
                    maximumPoolSize = 3
                    minimumIdle = 1
                    idleTimeout = 10001
                    maxLifetime = 300000
                    isAutoCommit = false
                    transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                    validate()
                }
            )
    }

    private fun runFlywayMigrations() =
        Flyway.configure().run {
            dataSource(
                "jdbc:postgresql://${environmentVariables.databaseHostUrl}:5432/postgres",
                "postgres",
                "postgres"
            )
            load().migrate()
        }
}

fun <T> ResultSet.toList(mapper: ResultSet.() -> T) =
    mutableListOf<T>().apply {
        while (next()) {
            add(mapper())
        }
    }

interface DatabaseInterface {
    val connection: Connection
}
