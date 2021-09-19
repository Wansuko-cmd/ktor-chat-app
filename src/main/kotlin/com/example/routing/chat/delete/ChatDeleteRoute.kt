package com.example.routing.chat.delete

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatDeleteRoute(chatService: ChatServiceInterface){

    delete {
        val chatDeleteRequest = call.receive<ChatDeleteRequest>()

        if(chatService.deleteMessage(chatDeleteRequest.id)){
            call.respondText("SUCCESS")
        }else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
