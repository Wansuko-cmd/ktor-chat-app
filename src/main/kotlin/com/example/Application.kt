package com.example

import com.example.routing.mainRoute
import io.ktor.application.*

fun main(args: Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){

    mainRoute()
}
