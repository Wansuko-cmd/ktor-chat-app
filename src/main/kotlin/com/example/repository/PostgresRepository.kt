package com.example.repository

import com.example.db.dsl.messages.MessagesDslInterface
import com.example.domain.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PostgresRepository: BaseRepositoryInterface, KoinComponent {

    private val messagesDsl by inject<MessagesDslInterface>()

    override suspend fun insertMessage(message: Message): Boolean = withContext(Dispatchers.IO){

        messagesDsl.insert(message)

        true
    }

    override suspend fun getAllMessages(): List<Message> = withContext(Dispatchers.IO){
        messagesDsl.getAll()
    }

    override suspend fun getMessages(limit: Int): List<Message> = withContext(Dispatchers.IO){
        messagesDsl.get(limit)
    }

    override suspend fun getMessageById(messageId: String): Message = withContext(Dispatchers.IO) {
        messagesDsl.getById(messageId)
    }

    override suspend fun updateMessage(message: Message): Int = withContext(Dispatchers.IO){
        messagesDsl.update(message)
    }

    override suspend fun deleteMessage(messageId: String): Int = withContext(Dispatchers.IO){
        messagesDsl.delete(messageId)
    }
}
