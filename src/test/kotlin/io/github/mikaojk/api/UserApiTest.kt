package io.github.mikaojk.api

import io.github.mikaojk.plugins.configureContentNegotiation
import io.github.mikaojk.services.UserInDB
import io.github.mikaojk.services.UserRequest
import io.github.mikaojk.services.UserService
import io.github.mikaojk.util.TestDB
import io.github.mikaojk.util.dropData
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson3.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tools.jackson.databind.json.JsonMapper
import tools.jackson.module.kotlin.jacksonMapperBuilder
import io.ktor.http.HttpHeaders.Accept as AcceptHeader
import io.ktor.http.HttpHeaders.ContentType as ContentTypeHeader


internal class UserApiTest {

    companion object {

        var jsonMapper: JsonMapper = jacksonMapperBuilder().build()

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

    @Test
    internal fun `Returns OK when input to create user is correct`() {
        testApplication {
            application {
                configureContentNegotiation()
                routing {
                    registerUserApi(userService)
                }
            }

            val userRequest = UserRequest("Joakim", "joakimkartveit@gmail.com")

            val response =
                client.post("/user") {
                    header(ContentTypeHeader, ContentType.Application.Json)
                    header(AcceptHeader, ContentType.Application.Json)
                    setBody(jsonMapper.writeValueAsString(userRequest))
                }

            assertEquals(HttpStatusCode.OK, response.status)
        }
    }

    @Test
    internal fun `Returns all users in db`() {
        testApplication {

            application {
                configureContentNegotiation()
                routing {
                    registerUserApi(userService)
                }
            }


            val userRequestJoakim = UserRequest("Joakim", "joakimkartveit@gmail.com")
            userService.saveUser(userRequestJoakim)
            val userRequestPer = UserRequest("Per", "per@gmail.com")
            userService.saveUser(userRequestPer)

            val client = createClient {
                install(ContentNegotiation) {
                    jackson {
                    }
                }
            }


            val response = client.get("/users")

            assertEquals(HttpStatusCode.OK, response.status)
            assertEquals(2, response.body<List<UserInDB>>().size)
        }
    }
}
