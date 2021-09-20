package com.wsr.routing.chat.update

import com.wsr.allowContentType
import com.wsr.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatUpdateRoute(chatService: ChatServiceInterface) {

    allowContentType(listOf(ContentType.Application.Json)) {

        put {
            val (id, userName, text) = call.receive<ChatUpdateRequest>()

            chatService.updateMessage(id, userName, text)

            call.respondText("Success")
        }
    }
}
