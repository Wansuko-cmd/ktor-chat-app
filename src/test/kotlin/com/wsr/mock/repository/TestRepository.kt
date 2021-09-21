package com.wsr.mock.repository

import com.wsr.domain.Message
import com.wsr.mock.data.TestMessageData
import com.wsr.repository.BaseRepositoryInterface
import com.wsr.service.datetime.DatetimeService.now
import kotlinx.datetime.LocalDateTime

class TestRepository: BaseRepositoryInterface {

    companion object {
        private val messagesData = TestMessageData.messagesData
    }

    override suspend fun insertMessage(message: Message): Boolean {
        messagesData.add(message)
        return true
    }

    override suspend fun getAllMessages(): List<Message> {
        return messagesData.sortedBy { it.createdAt }
    }

    override suspend fun getMessageById(messageId: String): Message {
        return messagesData.first { it.id == messageId }
    }

    override suspend fun updateMessage(message: Message): Int {
        messagesData[messagesData.indexOfFirst { it.id == message.id }] = message
        return 1
    }

    override suspend fun deleteMessage(messageId: String): Int {

        val index = messagesData.indexOfFirst { it.id == messageId }

        return if (index != -1) {
            messagesData.removeAt(messagesData.indexOfFirst { it.id == messageId })
            1
        } else {
            0
        }
    }

}
