package com.wsr.routing

import com.wsr.routing.chat.chatRoute
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.mainRoute(){
    routing {
        chatRoute()
        get{
            call.respondText("Hello World")
        }
    }
}
