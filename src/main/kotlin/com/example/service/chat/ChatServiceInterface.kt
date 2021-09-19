package com.example.service.chat

import com.example.domain.Message

interface ChatServiceInterface {

    suspend fun createMessage(userName: String, text: String): Message

    suspend fun getAllMessages(): List<Message>

    suspend fun getMessages(limit: Int): List<Message>

    suspend fun getMessageById(messageId: String): Message

    suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean

    suspend fun deleteMessage(messageId: String): Boolean
}
