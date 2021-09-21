@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.create

import com.wsr.di.testModule
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

            //飛んできたリクエスト
            val json = Json.encodeToString(
                ChatCreateRequest(
                    "新しいユーザー名",
                    "新しいテキスト"
                )
            )

            handleRequest(HttpMethod.Post, "/chat") {
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(json)

            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                //DBに挿入されたかどうかを確認
                runBlocking {
                    //リスポンスを取得
                    val result = Json.decodeFromString<ChatCreateResponse>(response.content!!)

                    //DBに挿入されている値を取得
                    val baseRepository by inject<BaseRepositoryInterface>()
                    val databaseMessage = baseRepository.getMessageById(result.id)

                    assertEquals(ChatCreateResponse.fromMessage(databaseMessage), result)
                }
            }
        }
    }


    @Test
    fun ContentTypeがJsonではないときUnsupportedMediaTypeを返す() {

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {
            //飛んできたリクエスト
            val json = Json.encodeToString(
                ChatCreateRequest(
                    "新しいユーザー名",
                    "新しいテキスト"
                )
            )

            //ContentTypeがJsonではなくAny
            handleRequest(HttpMethod.Post, "/chat") {
                addHeader("Content-Type", ContentType.Application.Any.toString())
                setBody(json)
            }.apply {
                assertEquals(HttpStatusCode.UnsupportedMediaType, response.status())
            }
        }
    }
}
