package com.example.repository

import com.example.domain.Message

interface BaseRepositoryInterface {

    suspend fun insertMessage(message: Message): Boolean

    suspend fun getAllMessages(): List<Message>

    suspend fun getMessages(limit: Int): List<Message>

    suspend fun getMessageById(messageId: String): Message

    suspend fun updateMessage(message: Message): Int

    suspend fun deleteMessage(messageId: String): Int
}
