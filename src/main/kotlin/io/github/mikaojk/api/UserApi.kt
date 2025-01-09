package io.github.mikaojk.api

import io.github.mikaojk.services.UserRequest
import io.github.mikaojk.services.UserService
import io.ktor.http.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Routing.registerUserApi(userService: UserService) {
    post("/user") {
        val userRequest: UserRequest = call.receive()

        try {
            userService.saveUser(userRequest)
            call.respond(HttpStatusCode.OK, "User saved")
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                "user could not be saved in DB,$userRequest",
            )
        }
    }
    get("/users") {
        val users = userService.getAllUsers()
        call.respond(users)
    }
    get("/user/{id}") {
        val userId = call.parameters["id"]?.toInt()
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest, "id parameter is required")
        } else {
            val user = userService.getUserById(userId)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound, "user with id: $userId not found")
            } else {
                call.respond(user)
            }
        }
    }
    put("/user/{id}") {
        val userId = call.parameters["id"]?.toInt()
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest, "id parameter is required")
        } else {
            val userRequest: UserRequest = call.receive()
            val updateUserSuccess = userService.updateUser(userRequest, userId)
            if (!updateUserSuccess) {
                call.respond(HttpStatusCode.NotFound, "user with id: $userId not updated")
            } else {
                call.respond(HttpStatusCode.OK, "updated user with id: $userId")
            }
        }
    }
    delete("/user/{id}") {
        val userId = call.parameters["id"]?.toInt()
        if (userId == null) {
            call.respond(HttpStatusCode.BadRequest, "id parameter is required")
        } else {
            val deletedUserSuccess = userService.deleteUserById(userId)
            if (!deletedUserSuccess) {
                call.respond(HttpStatusCode.NotFound, "user with id: $userId not deleted")
            } else {
                call.respond(HttpStatusCode.OK, "deleted user with id: $userId")
            }
        }
    }
}
