package com.wsr.db.table

import com.wsr.domain.Message
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime


object Messages : Table("messages") {

    val id = varchar("id", 36)
    val userName = text("user_name")
    val text = text("text")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")

    override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toMessage() = Message(
    this[Messages.id],
    this[Messages.userName],
    this[Messages.text],
    this[Messages.createdAt].toKotlinLocalDateTime(),
    this[Messages.updatedAt].toKotlinLocalDateTime()
)
