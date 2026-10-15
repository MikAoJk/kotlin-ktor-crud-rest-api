package io.github.mikaojk.api

import io.github.mikaojk.services.UserRequest
import io.github.mikaojk.services.UserService
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.server.application.*
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import tools.jackson.databind.DeserializationFeature
import tools.jackson.module.kotlin.jacksonMapperBuilder

private val jsonMapper = jacksonMapperBuilder()
    .enable(DeserializationFeature.FAIL_ON_TRAILING_TOKENS)
    .build()

private suspend fun ApplicationCall.receiveUser(): UserRequest {
    val body = jsonMapper.readTree(receiveText())
    require(body.isObject) { "Expected a user object" }
    val name = body.get("name")
    val email = body.get("email")
    val id = body.get("id")
    require(name != null && name.isString && email != null && email.isString) {
        "name and email must be strings"
    }
    require(id == null || id.isNull || (id.isIntegralNumber && id.canConvertToInt())) {
        "id must be a 32-bit integer or null"
    }
    return UserRequest(name.stringValue(), email.stringValue())
}

// Rust labels success messages as JSON, but sends them as unquoted bytes.
// Its error responses have no Content-Type header.
suspend fun ApplicationCall.respondRustMessage(status: HttpStatusCode, message: String) {
    respond(object : OutgoingContent.ByteArrayContent() {
        override val status = status
        override val contentType = if (status == HttpStatusCode.OK) ContentType.Application.Json else null
        override fun bytes() = message.toByteArray(Charsets.UTF_8)
    })
}

fun Routing.registerUserApi(userService: UserService) {
    post("/users") {
        userService.saveUser(call.receiveUser())
        call.respondRustMessage(HttpStatusCode.OK, "User created")
    }
    get("/users") {
        call.respondRustMessage(HttpStatusCode.OK, jsonMapper.writeValueAsString(userService.getAllUsers()))
    }
    get("/users/{id}") {
        val userId = call.parameters["id"]!!.toInt()
        val user = userService.getUserById(userId)
        if (user == null) {
            call.respondRustMessage(HttpStatusCode.NotFound, "User not found")
        } else {
            call.respondRustMessage(HttpStatusCode.OK, jsonMapper.writeValueAsString(user))
        }
    }
    put("/users/{id}") {
        val userId = call.parameters["id"]!!.toInt()
        userService.updateUser(call.receiveUser(), userId)
        call.respondRustMessage(HttpStatusCode.OK, "User updated")
    }
    delete("/users/{id}") {
        val userId = call.parameters["id"]!!.toInt()
        if (userService.deleteUserById(userId)) {
            call.respondRustMessage(HttpStatusCode.OK, "User deleted")
        } else {
            call.respondRustMessage(HttpStatusCode.NotFound, "User not found")
        }
    }
}
