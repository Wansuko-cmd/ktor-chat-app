package com.example.routing.chat.delete

import kotlinx.serialization.Serializable

@Serializable
data class ChatDeleteRequest(
    val id: String
)
