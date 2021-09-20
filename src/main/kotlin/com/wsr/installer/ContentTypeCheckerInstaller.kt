package com.wsr.installer

import com.wsr.ContentTypeChecker
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

fun Application.contentTypeCheckerInstaller(){

    install(ContentTypeChecker){
        onError = {
            call.respond(
                HttpStatusCode.UnsupportedMediaType,
                """
                    ${call.request.contentType()}は許可されていません
                    許可されているContent-Typeは${it.joinToString(", ")}のみです
                """.trimIndent()
            )
        }
        continueOnError = false
    }
}
