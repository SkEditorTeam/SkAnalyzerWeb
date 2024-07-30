package me.glicz.skanalyzerweb.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.ratelimit.*
import kotlin.time.Duration.Companion.seconds

fun Application.configureRateLimit() {
    install(RateLimit) {
        register(RateLimitName("parse")) {
            rateLimiter(limit = 1, refillPeriod = 1.seconds)
        }
    }
}