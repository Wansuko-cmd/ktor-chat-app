package com.wsr.domain

import kotlinx.datetime.LocalDateTime

data class Message(
    val id: String,
    val userName: String,
    val text: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
