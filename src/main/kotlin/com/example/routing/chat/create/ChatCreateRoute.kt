package com.example.routing.chat.create

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatCreateRoute(chatService: ChatServiceInterface){

    post{
        val chatCreateJson = call.receive<ChatCreateRequest>()

        val message = chatService.createMessage(
            chatCreateJson.userName, chatCreateJson.text
        )

        call.respond(ChatCreateResponse.fromMessage(message))
    }
}
