val logbackVersion: String by project
val koinVersion: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.gitlab.arturbosch.detekt").version("1.23.0")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
}

group = "com.issueTracker"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        // logging
        implementation("ch.qos.logback:logback-classic:$logbackVersion")

        // Dependency Injection
        // Koin for Ktor
        implementation("io.insert-koin:koin-ktor:$koinVersion")
        // SLF4J Logger for Koin
        implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
    }
}
