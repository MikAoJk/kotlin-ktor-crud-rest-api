package io.github.mikaojk.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.github.mikaojk.services.UserInDB
import io.github.mikaojk.util.TestDB
import io.github.mikaojk.util.dropData
import io.github.mikaojk.services.UserRequest
import io.github.mikaojk.services.UserService
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders.Accept as AcceptHeader
import io.ktor.http.HttpHeaders.ContentType as ContentTypeHeader
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation as ContentNegotiationServer
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserApiTest {

    companion object {

        private val objectMapper: ObjectMapper =
            ObjectMapper()
                .registerKotlinModule()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        private val database = TestDB()
        private val userService = UserService(database)

        @AfterAll
        @JvmStatic
        internal fun afterAll() {
            database.connection.dropData()
            database.stop()
        }
    }

    @Test
    internal fun `Returns OK when input to create user is correct`() {
        testApplication {
            application {
                routing { registerUserApi(userService) }

                install(ContentNegotiationServer) {
                    jackson {
                        registerKotlinModule()
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }
                }
            }

            val userRequest = UserRequest("Joakim", "joakimkartveit@gmail.com")

            val response =
                client.post("/user") {
                    header(ContentTypeHeader, ContentType.Application.Json)
                    header(AcceptHeader, ContentType.Application.Json)
                    setBody(objectMapper.writeValueAsString(userRequest))
                }

            assertEquals(response.status, HttpStatusCode.OK)
        }
    }

    @Test
    internal fun `Returns all users in db`() {
        testApplication {
            application {
                routing { registerUserApi(userService) }

                install(ContentNegotiationServer) {
                    jackson {
                        registerKotlinModule()
                        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                    }
                }
            }


            val userRequestJoakim = UserRequest("Joakim", "joakimkartveit@gmail.com")
            userService.saveUser(userRequestJoakim)
            val userRequestPer = UserRequest("Per", "per@gmail.com")
            userService.saveUser(userRequestPer)

            val client = createClient {
                install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                    jackson {
                        registerKotlinModule()
                    }
                }
            }


            val response = client.get("/users")

            assertEquals(response.status, HttpStatusCode.OK)
            assertEquals(response.body<List<UserInDB>>().size, 2)
        }
    }
}
