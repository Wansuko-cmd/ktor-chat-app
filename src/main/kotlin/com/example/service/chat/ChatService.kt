package com.example.service.chat

import com.example.domain.Message
import com.example.repository.BaseRepositoryInterface
import com.example.service.datetime.DatetimeService.now
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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

    override suspend fun getAllMessages(): Flow<List<Message>>
    = baseRepository.getAllMessages()

    override suspend fun getMessages(limit: Int): Flow<List<Message>>
    = baseRepository.getMessages(limit)

    override suspend fun getMessageById(messageId: String): Flow<Message>
    = baseRepository.getMessageById(messageId)

    override suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean {

        baseRepository.getMessageById(messageId).collect {

            val message = Message(
                id = messageId,
                userName = userName,
                text = text,
                createdAt = it.createdAt,
                updatedAt = LocalDateTime.now()
            )

            baseRepository.updateMessage(message)

        }
        return true
    }

    override suspend fun deleteMessage(messageId: String): Boolean{

        return try {
            baseRepository.deleteMessage(messageId) == 1

        }catch (e: Exception){
            false
        }
    }
}
