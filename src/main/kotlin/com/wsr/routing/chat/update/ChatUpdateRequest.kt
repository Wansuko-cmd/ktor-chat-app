package com.wsr.routing.chat.update

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatUpdateRequest(
    val id: String,
    @SerialName("user_name")
    val userName: String,
    val text: String
)
