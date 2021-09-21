package com.wsr.routing.chat.update

import com.wsr.allowContentType
import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException

fun Route.chatUpdateRoute(chatService: MessageServiceInterface) {

    allowContentType(listOf(ContentType.Application.Json)) {

        put {

            try{
                //Jsonの内容を取得
                val (id, userName, text) = call.receive<ChatUpdateRequest>()

                //Updateに成功すれば
                if(chatService.updateMessage(id, userName, text)){
                    call.respond(HttpStatusCode.OK)
                }
                //Updateに失敗すれば
                else{
                    call.respond(HttpStatusCode.UnprocessableEntity)
                }

            }
            //シリアライザーが上手くいかなければ
            catch (e: SerializationException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
