package com.wsr.routing.chat.create

import com.wsr.allowContentType
import com.wsr.service.chat.ChatServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException

fun Route.chatCreateRoute(chatService: ChatServiceInterface){

    allowContentType(listOf(ContentType.Application.Json)){
        post{

            try{
                val (userName, text) = call.receive<ChatCreateRequest>()

                val message = chatService.createMessage(userName, text)

                call.respond(ChatCreateResponse.fromMessage(message))

            }catch (e: SerializationException){
                call.respond(HttpStatusCode.BadRequest, "OUT")
            }
        }
    }
}
