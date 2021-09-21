package com.wsr.installer

import io.ktor.application.*
import org.koin.core.module.Module

fun Application.mainInstaller(isTest: Boolean, testModule: Module?){

    koinInstaller(isTest, testModule)
    serializerInstaller()
    contentTypeCheckerInstaller()
}
