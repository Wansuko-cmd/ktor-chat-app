package com.wsr.routing.chat.read

import com.wsr.domain.Message
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatReadRespond(
    val id: String,
    @SerialName("user_name")
    val userName: String,
    val text: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
){

    companion object{
        fun fromMessage(message: Message): ChatReadRespond{
            return ChatReadRespond(
                message.id,
                message.userName,
                message.text,
                message.createdAt,
                message.updatedAt
            )
        }
    }
}
