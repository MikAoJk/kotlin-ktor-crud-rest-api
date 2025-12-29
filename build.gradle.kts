group = "io.github.MikAoJk"
version = "1.0.0"

val javaVersion = 25

val ktorVersion = "3.3.3"
val junitJupiterVersion = "6.0.1"
val logbackVersion = "1.5.23"
val logstashEncoderVersion = "9.0"
val hikariCPVersion = "7.0.2"
val flywayVersion = "11.20.0"
val embeddedPostgresVersion = "2.2.0"
val postgresVersion = "42.7.8"


plugins {
    kotlin("jvm") version "2.3.0"
    id("application")
}

application {
    mainClass.set("io.github.mikaojk.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {

    //Ktor
    implementation("io.ktor:ktor-server-cio:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    //Database
    implementation("com.zaxxer:HikariCP:$hikariCPVersion")
    compileOnly("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.flywaydb:flyway-database-postgresql:$flywayVersion")
    implementation("org.postgresql:postgresql:$postgresVersion")

    //Logs
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")

    //Test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("io.zonky.test:embedded-postgres:$embeddedPostgresVersion")
    testImplementation("io.ktor:ktor-client-content-negotiation-jvm:$ktorVersion")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}


kotlin {
    jvmToolchain(javaVersion)
}

tasks {
    withType<Test> {
        useJUnitPlatform {}
        testLogging {
            events("passed", "skipped", "failed")
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }

}
