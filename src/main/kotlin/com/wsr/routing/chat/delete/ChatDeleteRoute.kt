package com.wsr.routing.chat.delete

import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.chatDeleteRoute(chatService: MessageServiceInterface) {

    delete("{id}") {

        //idを取得
        val id = call.parameters["id"]

        if(id != null){
            //Messageを削除することができれば
            if(chatService.deleteMessage(id)){
                call.respond(HttpStatusCode.OK)
            }
            //削除できなければ
            else{
                call.respond(HttpStatusCode.UnprocessableEntity)
            }
        }
        //idが存在しなければ
        else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
