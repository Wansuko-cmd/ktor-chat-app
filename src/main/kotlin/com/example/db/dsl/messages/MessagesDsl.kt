package com.example.db.dsl.messages

import com.example.db.builder.DatabaseBuilderInterface
import com.example.db.table.Messages
import com.example.db.table.toMessage
import com.example.domain.Message
import kotlinx.datetime.toJavaLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MessagesDsl: MessagesDslInterface, KoinComponent {

    private val database: Database

    init {
        val databaseBuilder by inject<DatabaseBuilderInterface>()
        database = databaseBuilder.database
    }

    override suspend fun insert(message: Message) {

        return transaction(database) {
            Messages.insert {
                it[id] = message.id
                it[userName] = message.userName
                it[text] = message.text
                it[createdAt] = message.createdAt.toJavaLocalDateTime()
                it[updatedAt] = message.updatedAt.toJavaLocalDateTime()
            }
        }
    }

    override suspend fun getAll(): List<Message> {
        return transaction(database) {

            Messages.selectAll()
                .orderBy(Messages.createdAt)
                .map { it.toMessage() }
        }
    }

    override suspend fun get(limit: Int): List<Message> {
        return transaction(database) {

            Messages.selectAll()
                .orderBy(Messages.createdAt)
                .limit(limit)
                .map { it.toMessage() }
        }
    }

    override suspend fun getById(id: String): Message {
        return transaction(database) {

            Messages.select{ Messages.id eq id }
                .first()
                .toMessage()
        }
    }

    override suspend fun update(message: Message): Int {
        return transaction(database) {
            Messages.update({ Messages.id eq message.id }){
                it[userName] = message.userName
                it[text] = message.text
                it[updatedAt] = message.updatedAt.toJavaLocalDateTime()
            }
        }
    }

    override suspend fun delete(id: String): Int {
        return transaction(database) {
            Messages.deleteWhere { Messages.id eq id }
        }
    }
}
