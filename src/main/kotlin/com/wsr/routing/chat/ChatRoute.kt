package com.wsr.routing.chat

import com.wsr.routing.chat.create.chatCreateRoute
import com.wsr.routing.chat.delete.chatDeleteRoute
import com.wsr.routing.chat.read.chatReadRoute
import com.wsr.routing.chat.update.chatUpdateRoute
import com.wsr.service.chat.ChatServiceInterface
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.chatRoute(){

    val chatService by inject<ChatServiceInterface>()

    route("chat"){
        chatCreateRoute(chatService)
        chatReadRoute(chatService)
        chatUpdateRoute(chatService)
        chatDeleteRoute(chatService)
    }
}
