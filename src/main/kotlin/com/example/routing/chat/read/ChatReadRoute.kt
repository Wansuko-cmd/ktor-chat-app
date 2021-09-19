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
            chatService.getAllMessages().collect {
                it.map { message -> messages.add(ChatReadRespond.fromMessage(message)) }
            }
        }else{
            val limit = call.request.queryParameters["limit"]?.toIntOrNull()

            if(limit == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            chatService.getMessages(limit).collect {
                it.map { message -> messages.add(ChatReadRespond.fromMessage(message)) }
            }
        }

        call.respond(messages)
    }

    get("{id}"){

        val id = call.parameters["id"]

        if(id != null){
            chatService.getMessageById(id).collect {
                call.respond(ChatReadRespond.fromMessage(it))
            }
        }else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
