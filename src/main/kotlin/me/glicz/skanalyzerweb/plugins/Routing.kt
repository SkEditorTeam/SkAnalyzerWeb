package me.glicz.skanalyzerweb.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.glicz.skanalyzerweb.routes.parseRoute

fun Application.configureRouting() {
    routing {
        parseRoute()
    }
}
