@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.read

import com.wsr.di.testModule
import com.wsr.mock.data.TestMessageData
import com.wsr.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatReadTest {

    @Test
    fun 全てのMessageを読み取る場合(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){

            //期待される値
            val chatReadResponds = Json.encodeToString(
                TestMessageData.messagesData.map { ChatReadRespond.fromMessage(it) }
            )

            handleRequest(HttpMethod.Get, "/chat").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(chatReadResponds, response.content)
            }
        }
    }

    @Test
    fun 制限をつけて読み取る場合(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){
            //制限
            val limit = 5

            //期待される値
            val chatReadResponds = Json.encodeToString(
                TestMessageData.messagesData
                    .sortedBy { it.updatedAt }
                    .take(limit)
                    .map { ChatReadRespond.fromMessage(it) }
            )

            handleRequest(HttpMethod.Get, "/chat?limit=${limit}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(chatReadResponds, response.content)
            }
        }
    }

    @Test
    fun 制限として設定した値が数値に変換できないときBadRequestを返す(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){
            //制限の値に、数値に変換できない値を入れる
            val limit = "あ"

            handleRequest(HttpMethod.Get, "/chat?limit=${limit}").apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun 特定のMessageを取得する場合(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){

            //特定のMessage
            val messageData = TestMessageData.messagesData.first()

            //期待される値
            val chatReadRespond = Json.encodeToString(
                ChatReadRespond.fromMessage(TestMessageData.messagesData.first())
            )

            handleRequest(HttpMethod.Get, "/chat/${messageData.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(chatReadRespond, response.content)
            }
        }
    }

    @Test
    fun 存在しないidを指定した場合UnprocessableEntityを返す(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){

            val id = "存在しないid"

            //存在しないidを入れる
            handleRequest(HttpMethod.Get, "/chat/$id").apply {
                assertEquals(HttpStatusCode.UnprocessableEntity, response.status())
            }
        }
    }
}
