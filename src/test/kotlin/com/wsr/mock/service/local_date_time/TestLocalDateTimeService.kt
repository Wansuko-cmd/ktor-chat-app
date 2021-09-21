package com.wsr.mock.service.local_date_time

import com.wsr.service.datetime.local_date_time.LocalDateTimeServiceInterface
import kotlinx.datetime.LocalDateTime

class TestLocalDateTimeService: LocalDateTimeServiceInterface {
    override fun now(): LocalDateTime = LocalDateTime.parse("2005-05-05T15:00:00")
}
