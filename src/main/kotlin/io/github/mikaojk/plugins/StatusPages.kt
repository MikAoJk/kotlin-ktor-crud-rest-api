package io.github.mikaojk.plugins

import io.github.mikaojk.api.respondRawMessage
import io.github.mikaojk.logger
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*

fun Application.configureStatusPages() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            logger.error("Request failed", cause)
            call.respondRawMessage(HttpStatusCode.InternalServerError, "Internal error")
        }
        unhandled { call ->
            call.respondRawMessage(HttpStatusCode.NotFound, "404 not found")
        }
    }
}
