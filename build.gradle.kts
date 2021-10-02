val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val datetimeVersion: String by project
val exposedVersion: String by project
val hikariVersion: String by project
val postgresVersion: String by project
val flywayVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.5.30"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"

    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.wsr"
version = "0.0.1"
application {
    mainClass.set("com.wsr.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://wansuko-cmd.github.io/maven/") }
}

//tasks.create("stage") {
//    dependsOn("runShadow")
//}

dependencies {

    //Ktor
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")

    //Log
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    //Koin
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")

    //kotlinx-datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")

    //exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")

    //hikari
    implementation("com.zaxxer:HikariCP:$hikariVersion")

    //hikari用のpostgresのドライバー
    implementation("org.postgresql:postgresql:$postgresVersion")

    //flyway
    implementation("org.flywaydb:flyway-core:$flywayVersion")

    //Content-Type-Checker
    implementation("com.wsr:content-type-checker:0.0.4")

    //Test
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")

    //H2
    testImplementation("com.h2database:h2:1.4.200")
}
