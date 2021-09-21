package com.wsr.mock.service.local_date_time

import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
import kotlinx.datetime.LocalDateTime

class TestLocalDateTimeService: LocalDateTimeServiceInterface {
    override fun now(): LocalDateTime = LocalDateTime.parse("2000-01-01T13:00:00")
}
