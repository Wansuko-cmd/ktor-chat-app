package com.example.service.datetime

import com.example.service.datetime.local_date_time.LocalDateTimeServiceInterface
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DatetimeService: KoinComponent{

    private val localDateTimeService by inject<LocalDateTimeServiceInterface>()

    fun LocalDateTime.Companion.now(): LocalDateTime = localDateTimeService.now()
}
