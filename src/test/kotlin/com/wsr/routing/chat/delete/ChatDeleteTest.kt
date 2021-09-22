@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.delete

import com.wsr.di.testModule
import com.wsr.mock.data.TestMessageData
import com.wsr.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatDeleteTest {

    @Test
    fun 特定のMessageを削除する場合(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){
            //特定のMessage
            val id = TestMessageData.messagesData.first().id

            handleRequest(HttpMethod.Delete, "/chat/${id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun 存在しないidを指定した場合UnprocessableEntityを返す(){

        withTestApplication({
            module(isTest = true, testModule = testModule)
        }){
            val id = "存在しないid"

            handleRequest(HttpMethod.Delete, "/chat/$id").apply {
                assertEquals(HttpStatusCode.UnprocessableEntity, response.status())
            }
        }
    }
}
