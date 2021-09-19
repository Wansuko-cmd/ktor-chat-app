package com.example.installer

import com.example.db.builder.DatabaseBuilder
import com.example.db.builder.DatabaseBuilderInterface
import com.example.db.dsl.messages.MessagesDsl
import com.example.db.dsl.messages.MessagesDslInterface
import com.example.repository.BaseRepositoryInterface
import com.example.repository.PostgresRepository
import com.example.repository.TestRepository
import com.example.service.chat.ChatService
import com.example.service.chat.ChatServiceInterface
import com.example.service.datetime.local_date_time.LocalDateTimeService
import com.example.service.datetime.local_date_time.LocalDateTimeServiceInterface
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

@Suppress("UNUSED_VARIABLE")
fun Application.koinInstaller(){

    val testModule = module {

        factory<BaseRepositoryInterface> { TestRepository() }

        factory<ChatServiceInterface> { ChatService() }

        single<LocalDateTimeServiceInterface> { LocalDateTimeService() }

        single<DatabaseBuilderInterface> { DatabaseBuilder() }

        single<MessagesDslInterface> { MessagesDsl() }
    }

    val productModule = module {

        factory<BaseRepositoryInterface> { PostgresRepository() }

        factory<ChatServiceInterface> { ChatService() }

        single<LocalDateTimeServiceInterface> { LocalDateTimeService() }

        single<DatabaseBuilderInterface> { DatabaseBuilder() }

        single<MessagesDslInterface> { MessagesDsl() }
    }

    install(Koin){
        slf4jLogger()
        modules(productModule)
    }
}
