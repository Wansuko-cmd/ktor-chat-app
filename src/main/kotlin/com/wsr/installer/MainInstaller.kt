package com.wsr.installer

import io.ktor.application.*

fun Application.mainInstaller(){

    koinInstaller()
    serializerInstaller()
    contentTypeCheckerInstaller()
}
