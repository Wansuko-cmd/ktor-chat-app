@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.create

import com.wsr.di.testModule
import com.wsr.mock.data.TestMessageData
import com.wsr.module
import com.wsr.repository.BaseRepositoryInterface
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatCreateTest : KoinComponent {

    @Test
    fun Messageを新しく追加する() {

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {
            val chatCreateRequest = ChatCreateRequest(
                "testUserName",
                "testText"
            )
            val json = Json.encodeToString(chatCreateRequest)

            handleRequest(HttpMethod.Post, "/chat") {
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(json)

            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                runBlocking {

                    //DBに挿入されたかどうかを確認
                    val result = Json.decodeFromString<ChatCreateResponse>(response.content!!)

                    val baseRepository by inject<BaseRepositoryInterface>()

                    val afterMessage = baseRepository.getMessageById(result.id)

                    assertEquals(ChatCreateResponse.fromMessage(afterMessage), result)
                }
            }
        }
    }


    @Test
    fun ContentTypeがJsonではないときUnsupportedMediaTypeを返す() {

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {
            val chatCreateRequest = ChatCreateRequest(
                "testUserName",
                "testText"
            )
            val json = Json.encodeToString(chatCreateRequest)

            handleRequest(HttpMethod.Post, "/chat") {
                addHeader("Content-Type", ContentType.Application.Any.toString())
                setBody(json)
            }.apply {
                assertEquals(HttpStatusCode.UnsupportedMediaType, response.status())
            }
        }
    }
}
