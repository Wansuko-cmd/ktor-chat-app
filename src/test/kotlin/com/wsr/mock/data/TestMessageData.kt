package com.wsr.mock.data

import com.wsr.domain.Message
import kotlinx.datetime.LocalDateTime

class TestMessageData {

    companion object {

        val beforeTime = LocalDateTime.parse("2000-01-01T13:00:00")

        val afterTime = LocalDateTime.parse("2005-05-05T15:00:00")

        val messagesData = mutableListOf(
            Message(
                "id_1",
                "username_1",
                "text_1",
                beforeTime,
                beforeTime
            ),
            Message(
                "id_2",
                "username_2",
                "text_2",
                beforeTime,
                beforeTime
            ),
            Message(
                "id_3",
                "username_3",
                "text_3",
                beforeTime,
                beforeTime
            ),
            Message(
                "id_4",
                "username_4",
                "text_4",
                beforeTime,
                beforeTime
            ),
            Message(
                "id_5",
                "username_5",
                "text_5",
                beforeTime,
                beforeTime
            )
        )
    }
}
