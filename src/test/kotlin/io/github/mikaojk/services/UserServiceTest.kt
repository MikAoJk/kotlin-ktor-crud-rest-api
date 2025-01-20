package io.github.mikaojk.services

import io.github.mikaojk.util.TestDB
import io.github.mikaojk.util.dropData
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class UserServiceTest {

    companion object {
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
    internal fun `Should return user when saveUser`() {

        val expectedValidationResult = UserInDB(1, "Joakim", "joakimkartveit@gmail.com")

        val userRequest = UserRequest("Joakim", "joakimkartveit@gmail.com")

        userService.saveUser(userRequest)

        val usersInDB = userService.getUserById(1)

        assertEquals(expectedValidationResult.name, usersInDB?.name)
        assertEquals(expectedValidationResult.email, usersInDB?.email)
        assertEquals(expectedValidationResult.id, usersInDB?.id)
    }
}
