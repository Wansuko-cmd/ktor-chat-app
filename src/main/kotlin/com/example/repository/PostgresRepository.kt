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

    override suspend fun insertMessage(message: Message): Flow<Boolean> = withContext(Dispatchers.IO){

        launch {
            messagesDsl.insert(message)
        }

        flow {
            emit(true)
        }
    }

    override suspend fun getAllMessages(): Flow<List<Message>> = withContext(Dispatchers.IO){
        flow{ emit(messagesDsl.getAll()) }
    }

    override suspend fun getMessages(limit: Int): Flow<List<Message>> = withContext(Dispatchers.IO){
        flow { emit(messagesDsl.get(limit)) }
    }

    override suspend fun getMessageById(messageId: String): Flow<Message> = withContext(Dispatchers.IO) {
        flow { emit(messagesDsl.getById(messageId)) }
    }

    override suspend fun updateMessage(message: Message): Flow<Int> = withContext(Dispatchers.IO){
        flow { emit(messagesDsl.update(message)) }
    }

    override suspend fun deleteMessage(messageId: String): Flow<Int> = withContext(Dispatchers.IO){
        flow { emit(messagesDsl.delete(messageId)) }
    }
}
