package com.wsr.di

import com.wsr.db.builder.DatabaseBuilder
import com.wsr.db.builder.DatabaseBuilderInterface
import com.wsr.db.dsl.messages.MessagesDsl
import com.wsr.db.dsl.messages.MessagesDslInterface
import com.wsr.mock.repository.TestRepository
import com.wsr.mock.service.local_date_time.TestLocalDateTimeService
import com.wsr.repository.BaseRepositoryInterface
import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
import com.wsr.service.message.MessageService
import com.wsr.service.message.MessageServiceInterface
import org.koin.dsl.module


val testModule = module {

    factory<BaseRepositoryInterface> { TestRepository() }

    factory<MessageServiceInterface> { MessageService() }

    single<LocalDateTimeServiceInterface> { TestLocalDateTimeService() }

    single<DatabaseBuilderInterface> { DatabaseBuilder() }

    single<MessagesDslInterface> { MessagesDsl() }
}
