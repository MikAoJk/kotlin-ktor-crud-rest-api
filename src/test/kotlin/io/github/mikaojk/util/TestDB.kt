package io.github.mikaojk.util

import io.zonky.test.db.postgres.embedded.EmbeddedPostgres
import io.github.mikaojk.db.DatabaseInterface
import java.sql.Connection
import org.flywaydb.core.Flyway

class TestDB : DatabaseInterface {
    private var pg: EmbeddedPostgres? = null
    override val connection: Connection
        get() = pg!!.postgresDatabase.connection.apply { autoCommit = false }

    init {
        pg = EmbeddedPostgres.start()
        Flyway.configure().run { dataSource(pg?.postgresDatabase).load().migrate() }
    }

    fun stop() {
        pg?.close()
    }
}

fun Connection.dropData() {
    use { connection ->
        connection.prepareStatement("DELETE FROM users").executeUpdate()
        connection.commit()
    }
}
