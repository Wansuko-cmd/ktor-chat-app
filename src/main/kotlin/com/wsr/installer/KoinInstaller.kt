package com.wsr.installer

import com.wsr.db.builder.DatabaseBuilder
import com.wsr.db.builder.DatabaseBuilderInterface
import com.wsr.db.dsl.messages.MessagesDsl
import com.wsr.db.dsl.messages.MessagesDslInterface
import com.wsr.repository.BaseRepositoryInterface
import com.wsr.repository.PostgresRepository
import com.wsr.repository.TestRepository
import com.wsr.service.chat.ChatService
import com.wsr.service.chat.ChatServiceInterface
import com.wsr.service.datetime.local_date_time.LocalDateTimeService
import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
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
        modules(testModule)
    }
}
