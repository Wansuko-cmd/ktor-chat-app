package com.example.service.chat

import com.example.domain.Message
import com.example.repository.BaseRepositoryInterface
import com.example.service.datetime.DatetimeService.now
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatService: ChatServiceInterface, KoinComponent {

    private val baseRepository: BaseRepositoryInterface by inject()

    override suspend fun createMessage(userName: String, text: String): Message {

        val message = Message(
            userName = userName,
            text = text,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        baseRepository.insertMessage(message)

        return message
    }

    override suspend fun getAllMessages(): List<Message> = baseRepository.getAllMessages()

    override suspend fun getMessages(limit: Int): List<Message> = baseRepository.getMessages(limit)

    override suspend fun getMessageById(messageId: String): Message = baseRepository.getMessageById(messageId)

    override suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean {

        val oldMessage = baseRepository.getMessageById(messageId)

        val message = Message(
            id = messageId,
            userName = userName,
            text = text,
            createdAt = oldMessage.createdAt,
            updatedAt = LocalDateTime.now()
        )

        baseRepository.updateMessage(message)

        return true
    }

    override suspend fun deleteMessage(messageId: String): Boolean {

        val number = baseRepository.deleteMessage(messageId)

        return number == 1
    }
}
