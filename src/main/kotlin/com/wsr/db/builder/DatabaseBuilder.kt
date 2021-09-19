package com.wsr.db.builder

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

class DatabaseBuilder: DatabaseBuilderInterface {

    override lateinit var database: Database

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val driverClassName = appConfig.property("db.driverClassName").getString()
    private val jdbcUrl = appConfig.property("db.jdbcUrl").getString()
    private val username = appConfig.property("db.username").getString()
    private val password = appConfig.property("db.password").getString()

    init {
        val pool = hikari()

        database = Database.connect(hikari())

        migrate(pool)
    }

    private fun migrate(dataSource: DataSource){
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .load()

        flyway.info()
        flyway.migrate()
    }


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
