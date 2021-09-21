package com.wsr.service.message

import com.wsr.domain.Message
import com.wsr.repository.BaseRepositoryInterface
import com.wsr.service.datetime.DatetimeService.now
import com.wsr.service.message.exception.MessageNotFoundException
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

class MessageService: MessageServiceInterface, KoinComponent {

    private val baseRepository by inject<BaseRepositoryInterface>()

    override suspend fun createMessage(userName: String, text: String): Message {

        //Messageの新規作成
        val message = Message(
            id = UUID.randomUUID().toString(),
            userName = userName,
            text = text,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        //作成したMessageの永続化
        baseRepository.insertMessage(message)

        return message
    }

    override suspend fun getMessages(limit: Int?): List<Message> {

        val messages = baseRepository.getAllMessages()

        //制限がなければ
        return if(limit == null){
            messages.sortedBy { it.updatedAt }
        }
        //制限があれば
        else{
            messages.sortedBy { it.updatedAt }.take(limit)
        }
    }

    override suspend fun getMessageById(messageId: String): Message? {

        return try {

            //idからMessageを取得
            baseRepository.getMessageById(messageId)
        }
        //Messageが見つからなかった場合の処理
        catch (e: MessageNotFoundException) {
            null
        }
    }

    override suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean {

        return try {

            //idから永続化してあるメッセージを取得
            val oldMessage = baseRepository.getMessageById(messageId)

            //Messageを新しく作り直す
            val message = Message(
                id = messageId,
                userName = userName,
                text = text,
                createdAt = oldMessage.createdAt,
                updatedAt = LocalDateTime.now()
            )

            //Messageを更新
            baseRepository.updateMessage(message)

            true

        }
        //更新するMessageが見つからなかった場合
        catch (e: MessageNotFoundException) {
            false
        }
    }

    override suspend fun deleteMessage(messageId: String): Boolean {

        return try {

            //Messageを削除
            baseRepository.deleteMessage(messageId)

            true

        }
        //削除するMessageが見つからなかった場合
        catch (e: MessageNotFoundException) {
            false
        }
    }
}
