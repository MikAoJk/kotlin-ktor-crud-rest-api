package io.github.mikaojk

import io.github.mikaojk.plugins.configureContentNegotiation
import io.github.mikaojk.plugins.configureCors
import io.github.mikaojk.plugins.configureLifecycleHooks
import io.github.mikaojk.plugins.configureRouting
import io.github.mikaojk.plugins.configureStatusPages
import io.ktor.server.application.*
import io.ktor.server.cio.CIO
import io.ktor.server.engine.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("kotlin-ktor-crud-rest-api")

fun main() {
    val embeddedServer =
        embeddedServer(
            CIO,
            port = 8080,
            module = Application::module,
        )
    embeddedServer.start(true)
}

fun Application.module() {
    configureStatusPages()
    configureContentNegotiation()
    configureCors()
    configureRouting()
    configureLifecycleHooks()
}
