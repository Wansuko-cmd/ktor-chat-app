package com.wsr.db.dsl.messages

import com.wsr.domain.Message

interface MessagesDslInterface {

    suspend fun insert(message: Message)

    suspend fun getAll(): List<Message>

    suspend fun get(limit: Int): List<Message>

    suspend fun getById(id: String): Message

    suspend fun update(message: Message): Int

    suspend fun delete(id: String): Int
}
