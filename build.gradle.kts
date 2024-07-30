import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.roxymc.net/snapshots")
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-rate-limit")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-gson")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("me.glicz:skanalyzer-api:1.5-SNAPSHOT") {
        exclude("org.slf4j", "slf4j-simple")
    }
}

kotlin {
    jvmToolchain(21)
}

tasks {
    withType<KotlinCompile> {
        dependsOn(clean)
    }
}

application {
    mainClass = "me.glicz.skanalyzerweb.ApplicationKt"
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}