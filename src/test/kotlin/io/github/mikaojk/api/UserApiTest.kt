package io.github.mikaojk.api

import io.github.mikaojk.plugins.configureContentNegotiation
import io.github.mikaojk.plugins.configureStatusPages
import io.github.mikaojk.services.UserInDB
import io.github.mikaojk.services.UserService
import io.github.mikaojk.util.TestDB
import io.github.mikaojk.util.dropData
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.jackson.module.kotlin.jacksonMapperBuilder

internal class UserApiTest {
    companion object {
        private val mapper = jacksonMapperBuilder().build()
        private val database = TestDB()
        private val userService = UserService(database)

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            database.connection.dropData()
            database.stop()
        }
    }

    @BeforeEach
    internal fun beforeEach() {
        database.connection.dropData()
    }

    private fun ApplicationTestBuilder.configureApi() {
        application {
            configureStatusPages()
            configureContentNegotiation()
            routing { registerUserApi { userService } }
        }
    }

    private suspend fun assertMessage(response: HttpResponse, status: HttpStatusCode, body: String) {
        assertEquals(status, response.status)
        assertEquals(body, response.bodyAsText())
        assertEquals(
            if (status == HttpStatusCode.OK) "application/json" else null,
            response.headers[HttpHeaders.ContentType],
        )
    }

    @Test
    internal fun `CRUD responses mutations persist`() = testApplication {
        configureApi()
        val created = client.post("/users") {
            setBody("""{"name":"Original","email":"original@example.com"}""")
        }
        assertMessage(created, HttpStatusCode.OK, "User created")
        val id = userService.getAllUsers().single().id
        val read = client.get("/users/$id") { header(HttpHeaders.Accept, "text/plain") }
        assertEquals(HttpStatusCode.OK, read.status)
        assertEquals(ContentType.Application.Json, read.contentType()?.withoutParameters())
        val expected = mapper.readTree("""{"id":$id,"name":"Original","email":"original@example.com"}""")
        assertEquals(expected, mapper.readTree(read.bodyAsText()))
        val listed = client.get("/users") { header(HttpHeaders.Accept, "text/plain") }
        assertEquals(HttpStatusCode.OK, listed.status)
        assertEquals(ContentType.Application.Json, listed.contentType()?.withoutParameters())
        assertEquals(mapper.readTree("[$expected]"), mapper.readTree(listed.bodyAsText()))

        val updated = client.put("/users/$id") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody("""{"id":null,"name":"Updated","email":"updated@example.com"}""")
        }
        assertMessage(updated, HttpStatusCode.OK, "User updated")
        assertEquals(UserInDB(id, "Updated", "updated@example.com"), userService.getUserById(id))
        assertMessage(client.delete("/users/$id"), HttpStatusCode.OK, "User deleted")
        assertEquals(null, userService.getUserById(id))
        assertMessage(client.get("/users/$id"), HttpStatusCode.NotFound, "User not found")
        assertMessage(client.delete("/users/$id"), HttpStatusCode.NotFound, "User not found")
        val missingUpdate = client.put("/users/$id") {
            setBody("""{"name":"Missing","email":"missing@example.com"}""")
        }
        assertMessage(missingUpdate, HttpStatusCode.OK, "User updated")
        assertEquals("[]", client.get("/users").bodyAsText())
    }

    @Test
    internal fun `Invalid input and unknown routes match errors`() = testApplication {
        configureApi()
        for (id in listOf("invalid", "2147483648")) {
            for (response in listOf(
                client.get("/users/$id"),
                client.put("/users/$id") { setBody("""{"name":"A","email":"a@example.com"}""") },
                client.delete("/users/$id"),
            )) {
                assertMessage(response, HttpStatusCode.InternalServerError, "Internal error")
            }
        }
        for (body in listOf(
            "{", "{}", """{"name":"A","email":"a@example.com"} trailing""",
            """{"name":1,"email":"a@example.com"}""",
            """{"name":null,"email":"a@example.com"}""",
            """{"id":"1","name":"A","email":"a@example.com"}""",
            """{"id":2147483648,"name":"A","email":"a@example.com"}""",
        )) {
            for (response in listOf(
                client.post("/users") { setBody(body) },
                client.put("/users/1") { setBody(body) },
            )) {
                assertMessage(response, HttpStatusCode.InternalServerError, "Internal error")
            }
        }
        for (path in listOf("/user", "/user/1", "/unknown")) {
            assertMessage(client.get(path), HttpStatusCode.NotFound, "404 not found")
        }
    }
}
