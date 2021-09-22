package com.wsr.routing.chat.delete

import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async

fun Route.chatDeleteRoute(chatService: MessageServiceInterface) {

    delete("{id}") {

        //idを取得
        val id = call.parameters["id"]

        if(id != null){

            val isSuccess = async { chatService.deleteMessage(id) }

            //DBで削除をしている間に別の処理を実行
            proceed()

            //Messageを削除することができれば
            if(isSuccess.await()){
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
