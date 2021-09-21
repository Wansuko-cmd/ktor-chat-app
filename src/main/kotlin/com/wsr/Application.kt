@file:Suppress("unused")

package com.wsr

import com.wsr.installer.mainInstaller
import com.wsr.routing.mainRoute
import io.ktor.application.*
import org.koin.core.module.Module

fun main(args: Array<String>):Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(isTest: Boolean = false, testModule: Module? = null){

    //Installの設定
    mainInstaller(isTest, testModule)

    //Routeの設定
    mainRoute()
}
