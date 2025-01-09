package io.github.mikaojk.plugins

import io.github.mikaojk.logger
import io.ktor.server.application.*

fun Application.configureLifecycleHooks() {

    monitor.subscribe(ApplicationStarted) { logger.info("Application Started!") }
    monitor.subscribe(ApplicationStopped) { logger.info("Application Stoped!") }
}
