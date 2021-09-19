package com.wsr.service.datetime

import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatetimeService: KoinComponent{

    private val localDateTimeService by inject<LocalDateTimeServiceInterface>()

    fun LocalDateTime.Companion.now(): LocalDateTime = localDateTimeService.now()
}
