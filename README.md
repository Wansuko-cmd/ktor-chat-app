# Ktor-Chat-App

Ktorで作成した、簡単なAPIサーバーです。

内容自体はとても簡素ですが、しっかり設計してそれらしいアプリケーションに仕上げました。

## API

ルート: /chat

|Route|Method|内容|受け取る型|レスポンス(Json or Status Code)|
|----|----|----|----|----|
|/|GET|メッセージ内容の読み取り|limit:メッセージの上限（クエリ）|メッセージのリスト(Json)|
|/{id}|GET|特定のメッセージ内容の読み取り|メッセージのid（パラメーター）|メッセージ(Json)|
|/|POST|メッセージの作成|Json（{username: String, text: String}）|作成されたメッセージ(Json)|
|/|PUT|メッセージの更新|Json({id: String, username: String, text: String})|更新できたかどうか(Status Code)|
|/{id}|DELETE|メッセージの削除|メッセージのid(パラメーター）|削除できたかどうか(Status Code)|

詳しくはテストを記述しているのでそちらを参考してください
