package com.wsr.routing.chat.create

import com.wsr.allowContentType
import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.SerializationException

fun Route.chatCreateRoute(chatService: MessageServiceInterface) {

    allowContentType(ContentType.Application.Json) {
        post{
            try{
                //Jsonの内容を取得
                val (userName, text) = call.receive<ChatCreateRequest>()

                //Messageを作成して永続化
                val message = chatService.createMessage(userName, text)

                //作成したMessageを返す
                call.respond(ChatCreateResponse.fromMessage(message))

            }
            //シリアライザーが上手くいかなければ
            catch (e: SerializationException){
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
