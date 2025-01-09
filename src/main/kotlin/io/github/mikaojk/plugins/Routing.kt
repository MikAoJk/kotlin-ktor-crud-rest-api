package io.github.mikaojk.plugins

import io.github.mikaojk.EnvironmentVariables
import io.github.mikaojk.api.registerUserApi
import io.github.mikaojk.db.Database
import io.github.mikaojk.services.UserService
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    val environmentVariables = EnvironmentVariables()
    val database = Database(environmentVariables)
    val userService = UserService(database)

    routing {
        registerUserApi(userService)
        swaggerUI(path = "openapi", swaggerFile = "openapi/documentation.yaml")
    }
}
