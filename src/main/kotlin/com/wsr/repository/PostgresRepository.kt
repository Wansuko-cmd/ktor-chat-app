package com.wsr.repository

import com.wsr.db.dsl.messages.MessagesDslInterface
import com.wsr.domain.Message
import com.wsr.service.message.exception.MessageNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.NoSuchElementException

class PostgresRepository: BaseRepositoryInterface, KoinComponent {

    private val messagesDsl by inject<MessagesDslInterface>()

    override suspend fun insertMessage(message: Message): Boolean = withContext(Dispatchers.IO){

        messagesDsl.insert(message)

        true
    }

    override suspend fun getAllMessages(): List<Message> = withContext(Dispatchers.IO){
        messagesDsl.getAll()
    }

    override suspend fun getMessageById(messageId: String): Message = withContext(Dispatchers.IO) {
        try{
            messagesDsl.getById(messageId)
        }
        //DBで見つからなければ
        catch (e: NoSuchElementException){
            throw MessageNotFoundException(e.message)
        }
    }

    override suspend fun updateMessage(message: Message): Int = withContext(Dispatchers.IO){

        //更新した数を取得
        val count = messagesDsl.update(message)

        //更新した数が一つもなければ例外を投げる
        if(count <= 0) throw MessageNotFoundException()

        return@withContext count
    }

    override suspend fun deleteMessage(messageId: String): Int = withContext(Dispatchers.IO){

        //削除した数を取得
        val count = messagesDsl.delete(messageId)

        //削除した数が一つもなければ例外を投げる
        if(count <= 0) throw MessageNotFoundException()

        return@withContext count
    }
}
