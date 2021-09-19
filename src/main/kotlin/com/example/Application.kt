package com.example

import com.example.installer.mainInstaller
import com.example.routing.mainRoute
import io.ktor.application.*

fun main(args: Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){

    //Installの設定
    mainInstaller()

    //Routeの設定
    mainRoute()
}
