package com.example.routing.chat.delete

import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatDeleteRoute(chatService: ChatServiceInterface){

    delete("{id}") {

        val id = call.parameters["id"]

        if(id != null){

            if(chatService.deleteMessage(id)){
                call.respondText("SUCCESS")
            }else{
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
