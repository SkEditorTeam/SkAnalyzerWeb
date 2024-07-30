package me.glicz.skanalyzerweb

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.glicz.skanalyzer.AnalyzerFlag
import me.glicz.skanalyzer.SkAnalyzer
import me.glicz.skanalyzerweb.plugins.configureContentNegotiation
import me.glicz.skanalyzerweb.plugins.configureRateLimit
import me.glicz.skanalyzerweb.plugins.configureRouting
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

var skAnalyzer: SkAnalyzer = SkAnalyzer.builder()
    .flags(AnalyzerFlag.FORCE_VAULT_HOOK, AnalyzerFlag.FORCE_REGIONS_HOOK)
    .workingDirectory(File("SkAnalyzer"))
    .build()

fun main(args: Array<String>) {
    skAnalyzer.start().join()

    EngineMain.main(args)
}

fun Application.module() {
    configureContentNegotiation()
    configureRateLimit()
    configureRouting()
}
