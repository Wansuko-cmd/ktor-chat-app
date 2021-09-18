package com.example.repository

import com.example.domain.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestRepository: BaseRepositoryInterface {

    companion object {
        val messagesData = mutableListOf<Message>()
    }

    override suspend fun insertMessage(message: Message): Boolean {
        messagesData.add(message)
        return true
    }

    override suspend fun getAllMessages(): Flow<List<Message>> {
        return flow { emit(messagesData) }
    }

    override suspend fun getMessageById(messageId: String): Flow<Message> {
        return flow { emit(messagesData.first { it.id == messageId }) }
    }

    override suspend fun updateMessage(message: Message): Boolean {
        messagesData[messagesData.indexOfFirst { it.id == message.id }] = message
        return true
    }

    override suspend fun deleteMessage(messageId: String): Boolean {
        messagesData.removeAt(messagesData.indexOfFirst { it.id == messageId })
        return true
    }

}
