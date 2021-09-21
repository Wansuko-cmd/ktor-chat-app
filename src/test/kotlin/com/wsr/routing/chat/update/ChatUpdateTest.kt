@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.update

import com.wsr.di.testModule
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


        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {

            val testMessage = TestMessageData.messagesData.first()

            val json = Json.encodeToString(
                ChatUpdateRequest(
                    testMessage.id,
                    testMessage.userName,
                    "Update"
                )
            )

            handleRequest(HttpMethod.Put, "/chat"){
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(json)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                val afterMessage = TestMessageData.messagesData.first()
                assertEquals(
                    Json.encodeToString(
                        ChatUpdateRequest(afterMessage.id, afterMessage.userName, afterMessage.text)
                    ),
                    json
                )
            }
        }
    }


    @Test
    fun ContentTypeがJsonではないとき(){
        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {

            val testMessage = TestMessageData.messagesData.first()

            val json = Json.encodeToString(
                ChatUpdateRequest(
                    testMessage.id,
                    testMessage.userName,
                    "Update"
                )
            )

            handleRequest(HttpMethod.Put, "/chat"){
                addHeader("Content-Type", ContentType.Any.toString())
                setBody(json)
            }.apply {

                assertEquals(HttpStatusCode.UnsupportedMediaType, response.status())
            }
        }
    }


    @Test
    fun 間違ったidを指定したとき(){
        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {

            val testMessage = TestMessageData.messagesData.first()
            val json = Json.encodeToString(
                ChatUpdateRequest(
                    testMessage.id + "test",
                    testMessage.userName,
                    "Update"
                )
            )

            handleRequest(HttpMethod.Put, "/chat"){
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(json)
            }.apply {

                assertEquals(HttpStatusCode.UnprocessableEntity, response.status())
            }
        }
    }
}
