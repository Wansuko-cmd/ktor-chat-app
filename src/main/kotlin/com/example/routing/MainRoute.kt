package com.example.routing

import com.example.routing.chat.chatRoute
import io.ktor.application.*
import io.ktor.routing.*

fun Application.mainRoute(){
    routing {
        chatRoute()
    }
}
