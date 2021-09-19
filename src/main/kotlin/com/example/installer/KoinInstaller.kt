package com.example.installer

import com.example.repository.BaseRepositoryInterface
import com.example.repository.TestRepository
import com.example.service.chat.ChatService
import com.example.service.chat.ChatServiceInterface
import com.example.service.datetime.local_date_time.LocalDateTimeService
import com.example.service.datetime.local_date_time.LocalDateTimeServiceInterface
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.koinInstaller(){

    val testModule = module {

        factory<BaseRepositoryInterface> { TestRepository() }

        factory<ChatServiceInterface> { ChatService() }

        single<LocalDateTimeServiceInterface> { LocalDateTimeService() }
    }

    install(Koin){
        slf4jLogger()
        modules(testModule)
    }
}
