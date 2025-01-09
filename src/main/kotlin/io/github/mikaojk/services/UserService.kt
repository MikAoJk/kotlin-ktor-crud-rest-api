package io.github.mikaojk.services

import io.github.mikaojk.db.DatabaseInterface
import io.github.mikaojk.db.deleteUser
import io.github.mikaojk.db.getAllUsers
import io.github.mikaojk.db.getUserById
import io.github.mikaojk.db.saveUser
import io.github.mikaojk.db.updateUser

class UserService(private val database: DatabaseInterface) {

    fun saveUser(userRequest: UserRequest) {
        database.saveUser(userRequest)
    }

    fun getAllUsers(): MutableList<UserInDB> {
        return database.getAllUsers()
    }

    fun getUserById(id: Int): UserInDB? {
        return database.getUserById(id)
    }

    fun updateUser(user: UserRequest, userId: Int): Boolean {
        val updateUserSuccess: Boolean = database.updateUser(user, userId)
        return updateUserSuccess
    }

    fun deleteUserById(userId: Int): Boolean {
        return database.deleteUser(userId)
    }
}

data class UserInDB(val id: Int, val name: String, val email: String)

data class UserRequest(val name: String, val email: String)
