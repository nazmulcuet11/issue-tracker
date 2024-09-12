@file:Suppress("PropertyName", "VariableNaming")
val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project
val koin_version: String by project
val exposed_version: String by project
val postgres_version: String by project
val hikari_cp_version: String by project
val crypto_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.0"
    id("io.gitlab.arturbosch.detekt").version("1.23.0")
}

group = "com.issueTracker"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    // ktor server
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")

    // content negotiation
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")

    // Dependency Injection
    // Koin for Ktor
    implementation("io.insert-koin:koin-ktor:$koin_version")
    // SLF4J Logger for Koin
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // DB
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.zaxxer:HikariCP:$hikari_cp_version")

    // Authentication
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("org.springframework.security:spring-security-crypto:$crypto_version")

    // test
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
}
