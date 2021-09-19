package com.example.domain

import java.time.LocalDateTime
import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val userName: String,
    val text: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)