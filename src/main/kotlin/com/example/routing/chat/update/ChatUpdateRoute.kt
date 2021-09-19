package com.example.routing.chat.update

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatUpdateRoute(chatService: ChatServiceInterface){

    put {
        val (id, userName, text) = call.receive<ChatUpdateRequest>()

        chatService.updateMessage(id, userName, text)

        call.respondText("Success")
    }
}
