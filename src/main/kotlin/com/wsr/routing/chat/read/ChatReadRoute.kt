package com.wsr.routing.chat.read

import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NumberFormatException

fun Route.chatReadRoute(chatService: MessageServiceInterface) {

    get {

        try {

            val chatReadResponds = chatService
                //Messageを取得
                .getMessages(call.request.queryParameters["limit"]?.toInt())
                //ChatReadRespond型に変換
                .map { ChatReadRespond.fromMessage(it) }

            //Messageのリストを返す
            call.respond(chatReadResponds)

        }
        //limitが数値に変換できなければ
        catch (e: NumberFormatException){
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    //メッセージのidを指定された場合
    get("{id}"){

        //idの取得
        val id = call.parameters["id"]

        //idが指定されていれば
        if(id != null){

            //該当するidのMessageを取得
            val message =  chatService.getMessageById(id)

            //メッセージが存在すれば
            if(message != null){
                call.respond(ChatReadRespond.fromMessage(message))
            }
            //存在しなければ
            else{
                call.respond(HttpStatusCode.UnprocessableEntity)
            }

        }
        //idが指定されていなければ
        else{
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
