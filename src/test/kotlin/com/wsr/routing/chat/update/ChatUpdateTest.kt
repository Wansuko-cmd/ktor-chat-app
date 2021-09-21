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

            //アップデートするMessage
            val beforeUpdateMessage = TestMessageData.messagesData.first()

            //飛んできたリクエスト
            val json = Json.encodeToString(
                ChatUpdateRequest(
                    beforeUpdateMessage.id,
                    "更新するユーザー名",
                    "更新する内容"
                )
            )


            handleRequest(HttpMethod.Put, "/chat"){
                addHeader("Content-Type", ContentType.Application.Json.toString())
                setBody(json)
            }.apply {

                assertEquals(HttpStatusCode.OK, response.status())

                //DBの中身が更新されていることを確認
                runBlocking {

                    //DBに挿入されている値を取得
                    val baseRepository by inject<BaseRepositoryInterface>()

                    val databaseMessage = baseRepository.getMessageById(beforeUpdateMessage.id)

                    assertEquals("更新するユーザー名", databaseMessage.userName)
                    assertEquals("更新する内容", databaseMessage.text)
                    assertEquals(TestMessageData.beforeTime, databaseMessage.createdAt)
                    assertEquals(TestMessageData.afterTime, databaseMessage.updatedAt)
                }
            }
        }
    }


    @Test
    fun ContentTypeがJsonではないときUnsupportedMediaTypeを返す(){
        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {

            //アップデートするMessage
            val beforeUpdateMessage = TestMessageData.messagesData.first()

            //飛んできたリクエスト
            val json = Json.encodeToString(
                ChatUpdateRequest(
                    beforeUpdateMessage.id,
                    "更新するユーザー名",
                    "更新する内容"
                )
            )

            //ContentTypeがJsonではなくAny
            handleRequest(HttpMethod.Put, "/chat"){
                addHeader("Content-Type", ContentType.Any.toString())
                setBody(json)
            }.apply {

                assertEquals(HttpStatusCode.UnsupportedMediaType, response.status())

                //DBの中身が更新されていないことを確認
                runBlocking {

                    //DBに挿入されている値を取得
                    val baseRepository by inject<BaseRepositoryInterface>()

                    val databaseMessage = baseRepository.getMessageById(beforeUpdateMessage.id)

                    assertEquals(beforeUpdateMessage.userName, databaseMessage.userName)
                    assertEquals(beforeUpdateMessage.text, databaseMessage.text)
                    assertEquals(TestMessageData.beforeTime, databaseMessage.createdAt)
                    assertEquals(TestMessageData.beforeTime, databaseMessage.updatedAt)
                }
            }
        }
    }


    @Test
    fun 間違ったidを指定したときUnprocessableEntityを返す(){
        withTestApplication({
            module(isTest = true, testModule = testModule)
        }) {

            val json = Json.encodeToString(
                ChatUpdateRequest(
                    "存在しないid",
                    "更新するユーザー名",
                    "更新する内容"
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
