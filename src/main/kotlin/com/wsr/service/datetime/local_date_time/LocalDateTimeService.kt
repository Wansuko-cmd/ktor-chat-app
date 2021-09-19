package com.wsr.service.datetime.local_date_time

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class LocalDateTimeService: LocalDateTimeServiceInterface {

    override fun now(): LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
}
