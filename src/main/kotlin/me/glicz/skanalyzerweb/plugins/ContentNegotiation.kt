package me.glicz.skanalyzerweb.plugins

import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import me.glicz.skanalyzer.util.serialize.Serialization

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        register(
            ContentType.Application.Json,
            GsonConverter(Serialization.GSON)
        )
    }
}