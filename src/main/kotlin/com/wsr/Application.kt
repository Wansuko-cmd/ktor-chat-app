@file:Suppress("unused")

package com.wsr

import com.wsr.installer.mainInstaller
import com.wsr.routing.mainRoute
import io.ktor.application.*

fun main(args: Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){

    //Installの設定
    mainInstaller()

    //Routeの設定
    mainRoute()
}
