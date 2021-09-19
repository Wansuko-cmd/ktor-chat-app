package com.example.routing.chat

import com.example.routing.chat.create.chatCreateRoute
import com.example.routing.chat.delete.chatDeleteRoute
import com.example.routing.chat.read.chatReadRoute
import com.example.routing.chat.update.chatUpdateRoute
import com.example.service.chat.ChatServiceInterface
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
