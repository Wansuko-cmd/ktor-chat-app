package com.example.routing.chat.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatCreateRequest(
    @SerialName("user_name")
    val userName: String,
    val text: String,
)
