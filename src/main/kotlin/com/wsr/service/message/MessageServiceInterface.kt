package com.wsr.service.message

import com.wsr.domain.Message

interface MessageServiceInterface {

    /**
     * Messageを新規作成し、永続化する
     *
     * 第一引数：ユーザー名
     * 第二引数：内容
     *
     * 返り値：作成したMessage
     */
    suspend fun createMessage(userName: String, text: String): Message


    /**
     * 永続化してあるメッセージを取得する関数
     *
     * 第一引数：取得するMessageの上限（デフォルト：上限なし）
     *
     * 返り値：取得したMessageのリスト
     */
    suspend fun getMessages(limit: Int? = null): List<Message>


    /**
     * 永続化してあるMessageから、特定のMessageを取得する関数
     *
     * 第一引数：取得するMessageのid
     *
     * 返り値：取得したMessage（取得できなければnull）
     */
    suspend fun getMessageById(messageId: String): Message?


    /**
     * 永続化してあるMessageを更新する関数
     *
     * 第一引数：更新するMessageのid
     * 第二引数：更新後のユーザー名
     * 第三引数：更新後の内容
     *
     * 返り値：成功したかどうか
     */
    suspend fun updateMessage(messageId: String, userName: String, text: String): Boolean


    /**
     * 永続化してあるMessageの中の、特定のMessageを削除する関数
     *
     * 第一引数：削除するMessageのid
     *
     * 返り値：成功したかどうか
     */
    suspend fun deleteMessage(messageId: String): Boolean
}
