package com.example.repository

import com.example.domain.Message
import com.example.service.datetime.DatetimeService.now
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDateTime

class TestRepository: BaseRepositoryInterface {

    companion object {
        private val messagesData = mutableListOf(
            Message(
                "b637529e-3509-4015-853e-c9d61704a32f",
                "test",
                "Hello World",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "78f10f0d-7907-4793-a66c-02e782c24af7",
                "test1",
                "Hello",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "03686246-b447-4e0f-8950-6c904ab72b4b",
                "test2",
                "Hello World!",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "5eff2a8e-dfbf-44ce-9e1a-8c57bbf4081d",
                "test1",
                "Hi!",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "d6cc1bf8-2973-4246-b386-44f832fa801a",
                "test",
                "Good to see you!",
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        )
    }

    override suspend fun insertMessage(message: Message): Boolean {
        messagesData.add(message)
        return true
    }

    override suspend fun getAllMessages(): Flow<List<Message>> {
        return flow { emit(messagesData.sortedBy { it.createdAt }) }
    }

    override suspend fun getMessages(limit: Int): Flow<List<Message>> {
        return flow { emit(messagesData.sortedBy { it.createdAt }.take(limit)) }
    }

    override suspend fun getMessageById(messageId: String): Flow<Message> {
        return flow { emit(messagesData.first { it.id == messageId }) }
    }

    override suspend fun updateMessage(message: Message): Boolean {
        messagesData[messagesData.indexOfFirst { it.id == message.id }] = message
        return true
    }

    override suspend fun deleteMessage(messageId: String): Int {

        val index = messagesData.indexOfFirst { it.id == messageId }

        return if(index != -1) {
            messagesData.removeAt(messagesData.indexOfFirst { it.id == messageId })
            1
        }else{
            0
        }
    }

}
