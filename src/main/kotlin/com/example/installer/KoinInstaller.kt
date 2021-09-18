package com.example.installer

import com.example.repository.BaseRepository
import com.example.repository.TestRepository
import com.example.service.chat.ChatService
import com.example.service.chat.ChatServiceInterface
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.koinInstaller(){

    val testModule = module {

        factory<BaseRepository> { TestRepository() }

        factory<ChatServiceInterface> { ChatService() }
    }

    install(Koin){
        slf4jLogger()
        modules(testModule)
    }
}
