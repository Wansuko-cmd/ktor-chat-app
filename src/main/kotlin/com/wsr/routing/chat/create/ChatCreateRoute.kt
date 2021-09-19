package com.wsr.routing.chat.create

import com.wsr.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatCreateRoute(chatService: ChatServiceInterface){

    post{
        val (userName, text) = call.receive<ChatCreateRequest>()

        val message = chatService.createMessage(userName, text)

        call.respond(ChatCreateResponse.fromMessage(message))
    }
}
