package com.example.db.builder

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

class DatabaseBuilder: DatabaseBuilderInterface {

    override val database: Database by lazy {
        Database.connect(hikari())
    }

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val driverClassName = appConfig.property("db.driverClassName").getString()
    private val jdbcUrl = appConfig.property("db.jdbcUrl").getString()
    private val username = appConfig.property("db.username").getString()
    private val password = appConfig.property("db.password").getString()

    init {
        migrate()
    }

    private fun migrate() = Flyway.configure()
        .dataSource(jdbcUrl, username, password)
        .load()
        .migrate()


    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = this@DatabaseBuilder.driverClassName
            jdbcUrl = this@DatabaseBuilder.jdbcUrl
            username = this@DatabaseBuilder.username
            password = this@DatabaseBuilder.password
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
}
