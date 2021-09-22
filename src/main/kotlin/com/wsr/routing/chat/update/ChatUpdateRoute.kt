package com.wsr.routing.chat.update

import com.wsr.allowContentType
import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async
import kotlinx.serialization.SerializationException

fun Route.chatUpdateRoute(chatService: MessageServiceInterface) {

    allowContentType(ContentType.Application.Json) {

        put {

            try{
                //Jsonの内容を取得
                val (id, userName, text) = call.receive<ChatUpdateRequest>()

                //Messageを更新
                val isSuccess = async{ chatService.updateMessage(id, userName, text) }

                //DBに保存している間に別の処理を実行
                proceed()

                //Updateに成功すれば
                if(isSuccess.await()){
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
