@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.update

import com.wsr.di.testModule
import com.wsr.domain.Message
import com.wsr.mock.data.TestMessageData
import com.wsr.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatUpdateTest {

    @Test
    fun 正常にアップデートできたとき(){

        chatUpdateTest(
            request = {

                val testMessage = messagesData.first()

                val json = Json.encodeToString(
                    ChatUpdateRequest(
                        testMessage.id,
                        testMessage.userName,
                        testMessage.text
                    )
                )

                addHeader("Content-Type", "application/json")
                setBody(json)
            }
        ) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }

    @Test
    fun ContentTypeがJsonではないとき(){

    }

    @Test
    fun 間違ったidを指定したとき(){

    }

    companion object{

        private lateinit var messagesData: List<Message>

        private fun chatUpdateTest(request: TestApplicationRequest.() -> Unit, call: TestApplicationCall.() -> Unit) {
            withTestApplication({
                module(isTest = true, testModule = testModule)
                messagesData = TestMessageData.messagesData
            }) {
                handleRequest(HttpMethod.Put, "/chat", request).apply{
                    call(this)
                }
            }
        }
    }
}
