package com.wsr.domain

import kotlinx.datetime.LocalDateTime
import java.util.*

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val userName: String,
    val text: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
