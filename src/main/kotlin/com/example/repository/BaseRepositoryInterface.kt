package com.example.repository

import com.example.domain.Message
import kotlinx.coroutines.flow.Flow

interface BaseRepositoryInterface {

    suspend fun insertMessage(message: Message): Boolean

    suspend fun getAllMessages(): Flow<List<Message>>

    suspend fun getMessageById(messageId: String): Flow<Message>

    suspend fun updateMessage(message: Message): Boolean

    suspend fun deleteMessage(messageId: String): Boolean
}
