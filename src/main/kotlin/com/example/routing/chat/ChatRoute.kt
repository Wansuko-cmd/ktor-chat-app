package com.example.routing.chat

import com.example.routing.chat.create.chatCreateRoute
import com.example.routing.chat.delete.chatDeleteRoute
import com.example.routing.chat.read.chatReadRoute
import com.example.routing.chat.update.chatUpdateRoute
import io.ktor.routing.*

fun Route.chatRoute(){

    route("chat"){
        chatCreateRoute()
        chatReadRoute()
        chatUpdateRoute()
        chatDeleteRoute()
    }
}
