package com.wsr.mock.data

import com.wsr.domain.Message
import com.wsr.service.datetime.DatetimeService.now
import kotlinx.datetime.LocalDateTime

class TestMessageData {

    companion object {
        val messagesData = mutableListOf(
            Message(
                "id_1",
                "username_1",
                "text_1",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "id_2",
                "username_2",
                "text_2",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "id_3",
                "username_3",
                "text_3",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "id_4",
                "username_4",
                "text_4",
                LocalDateTime.now(),
                LocalDateTime.now()
            ),
            Message(
                "id_5",
                "username_5",
                "text_5",
                LocalDateTime.now(),
                LocalDateTime.now()
            )
        )
    }
}
