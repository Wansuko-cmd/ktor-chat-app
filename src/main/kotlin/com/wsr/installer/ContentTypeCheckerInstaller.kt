package com.wsr.installer

import com.wsr.ContentTypeChecker
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

fun Application.contentTypeCheckerInstaller(){

    install(ContentTypeChecker){
        onErrorWhenAllow = {
            call.respond(
                HttpStatusCode.UnsupportedMediaType,
                """
                    ${call.request.contentType()} is not allowed Content Type.
                    Use [${it.joinToString(", ")}] Content Type.
                """.trimIndent()
            )
        }
        continueOnError = false
    }
}
