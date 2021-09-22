package com.wsr.routing.chat.read

import com.wsr.service.message.MessageServiceInterface
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.async
import java.lang.NumberFormatException

fun Route.chatReadRoute(chatService: MessageServiceInterface) {

    get {

        try {

            //上限の値を取得(nullの時は無制限)
            val limit = call.request.queryParameters["limit"]?.toInt()

            val chatReadResponds = async {

                chatService
                    //Messageを取得
                    .getMessages(limit)
                    //ChatReadRespond型に変換
                    .map { ChatReadRespond.fromMessage(it) }
            }

            //DBで取得している間に別の処理を実行
            proceed()

            //Messageのリストを返す
            call.respond(chatReadResponds.await())

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

            //該当するidのMessageを取得する処理
            val messageDeferred = async { chatService.getMessageById(id) }

            //DBで取得している間に別の処理を実行
            proceed()

            //実際のメッセージの取得
            val message = messageDeferred.await()

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
