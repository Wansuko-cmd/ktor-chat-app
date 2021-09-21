@file:Suppress("NonAsciiCharacters", "TestFunctionName")

package com.wsr.routing.chat.update

import com.wsr.di.testModule
import com.wsr.mock.data.TestMessageData
import com.wsr.module
import com.wsr.repository.BaseRepositoryInterface
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatUpdateTest : KoinComponent {

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

                runBlocking {

                    //DBの中身が更新されていることを確認
                    val baseRepository by inject<BaseRepositoryInterface>()

                    val afterMessage = baseRepository.getMessageById(testMessage.id)

                    assertEquals("Update", afterMessage.text)
                }
            }
        }
    }


    @Test
    fun ContentTypeがJsonではないときUnsupportedMediaTypeを返す(){
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

                runBlocking {

                    //DBの中身が更新されていないことを確認
                    val baseRepository by inject<BaseRepositoryInterface>()

                    val afterMessage = baseRepository.getMessageById(testMessage.id)

                    assertEquals(testMessage.text, afterMessage.text)
                }
            }
        }
    }


    @Test
    fun 間違ったidを指定したときUnprocessableEntityを返す(){
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

                runBlocking {

                    //DBの中身が更新されていないことを確認
                    val baseRepository by inject<BaseRepositoryInterface>()

                    val afterMessage = baseRepository.getMessageById(testMessage.id)

                    assertEquals(testMessage.text, afterMessage.text)
                }
            }
        }
    }
}
