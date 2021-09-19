package com.example.routing.chat.read

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.flow.collect

fun Route.chatReadRoute(chatService: ChatServiceInterface){
    get {

        val messages: MutableList<ChatReadRespond> = mutableListOf()

        if(call.request.queryParameters["limit"] == null){
            chatService.getAllMessages()
                .map { messages.add(ChatReadRespond.fromMessage(it)) }
        }else{
            val limit = call.request.queryParameters["limit"]?.toIntOrNull()

            if(limit == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            chatService.getMessages(limit)
                .map { messages.add(ChatReadRespond.fromMessage(it)) }
        }

        call.respond(messages)
    }

    get("{id}"){

        val id = call.parameters["id"]

        if(id != null){
            call.respond(
                ChatReadRespond.fromMessage(chatService.getMessageById(id))
            )
        }else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
