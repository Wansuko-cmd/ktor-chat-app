package com.wsr.mock.db

import com.wsr.db.builder.DatabaseBuilderInterface
import com.wsr.db.table.Messages
import com.wsr.domain.Message
import com.wsr.mock.data.TestMessageData
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object TestDatabaseBuilder: DatabaseBuilderInterface {

    override val database: Database

    init {
        database = connectDatabase()
        createTable(database)
        seeding(database, TestMessageData.messagesData)
    }

    private fun connectDatabase(): Database{
        return Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            driver = "org.h2.Driver",
            user = "sa",
            password = ""
        )
    }

    private fun createTable(database: Database){
        transaction(database) {
            SchemaUtils.create(Messages)
        }
    }

    private fun seeding(database: Database, messages: List<Message>){

        transaction(database) {

            messages.map { message ->
                Messages.insert {
                    it[id] = message.id
                    it[userName] = message.userName
                    it[text] = message.text
                    it[createdAt] = message.createdAt.toJavaLocalDateTime()
                    it[updatedAt] = message.updatedAt.toJavaLocalDateTime()
                }
            }
        }
    }
}
