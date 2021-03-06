package com.wsr.installer

import com.wsr.db.builder.DatabaseBuilder
import com.wsr.db.builder.DatabaseBuilderInterface
import com.wsr.db.dsl.messages.MessagesDsl
import com.wsr.db.dsl.messages.MessagesDslInterface
import com.wsr.repository.BaseRepositoryInterface
import com.wsr.repository.PostgresRepository
import com.wsr.service.message.MessageService
import com.wsr.service.message.MessageServiceInterface
import com.wsr.service.datetime.local_date_time.LocalDateTimeService
import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.koinInstaller(isTest: Boolean, testModule: Module?){

    val productModule = module {

        factory<BaseRepositoryInterface> { PostgresRepository() }

        factory<MessageServiceInterface> { MessageService() }

        single<LocalDateTimeServiceInterface> { LocalDateTimeService() }

        single<DatabaseBuilderInterface> { DatabaseBuilder() }

        single<MessagesDslInterface> { MessagesDsl() }
    }

    install(Koin){
        slf4jLogger()
        if(isTest && testModule != null) modules(testModule)
        else modules(productModule)
    }
}
