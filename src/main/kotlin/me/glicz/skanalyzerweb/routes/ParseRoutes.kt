package me.glicz.skanalyzerweb.routes

import com.google.common.base.Throwables
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.glicz.skanalyzer.result.ScriptAnalyzeResults
import me.glicz.skanalyzerweb.scheduler
import me.glicz.skanalyzerweb.skAnalyzer
import java.io.File
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

lateinit var parseFuture: CompletableFuture<ScriptAnalyzeResults>

fun Route.parseRoute() {
    rateLimit(RateLimitName("parse")) {
        post("/parse") {
            val script = call.receiveText()

            try {
                parseFuture.join()
            } catch (_: Exception) {
            }

            val file =
                File.createTempFile("skanalyzerweb_" + System.currentTimeMillis(), ".sk").apply { deleteOnExit() }
            file.writeText(script)

            try {
                parseFuture = skAnalyzer.parseScript(file.absolutePath)
                scheduler.schedule({ parseFuture.cancel(true) }, 10, TimeUnit.SECONDS)

                call.respond(parseFuture.join().results[file]!!.toJsonObject().asMap())
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, Throwables.getStackTraceAsString(e))
            }

            file.delete()
        }
    }
}