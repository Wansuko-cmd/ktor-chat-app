package com.example.routing.chat.create

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.first

fun Route.chatCreateRoute(chatService: ChatServiceInterface){

    post{
        val (userName, text) = call.receive<ChatCreateRequest>()

        val message = chatService.createMessage(userName, text)

        call.respond(ChatCreateResponse.fromMessage(message))
    }
}
