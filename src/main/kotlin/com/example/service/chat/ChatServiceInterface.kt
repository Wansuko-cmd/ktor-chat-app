package com.example.service.chat

import com.example.domain.Message
import kotlinx.coroutines.flow.Flow

interface ChatServiceInterface {

    suspend fun createMessage(userName: String, text: String): Flow<Message>

    suspend fun getAllMessages(): Flow<List<Message>>

    suspend fun getMessages(limit: Int): Flow<List<Message>>

    suspend fun getMessageById(messageId: String): Flow<Message>

    suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean

    suspend fun deleteMessage(messageId: String): Flow<Boolean>
}
