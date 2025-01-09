package io.github.mikaojk.db

import io.github.mikaojk.services.UserInDB
import io.github.mikaojk.services.UserRequest
import java.sql.ResultSet

fun DatabaseInterface.saveUser(userRequest: UserRequest) =
    connection.use { connection ->
        connection
            .prepareStatement(
                """
                INSERT INTO users(name, email) VALUES (?, ?);
                """,
            )
            .use { preparedStatement ->
                preparedStatement.setString(1, userRequest.name)
                preparedStatement.setString(2, userRequest.email)
                preparedStatement.executeUpdate()
            }
        connection.commit()
    }

fun DatabaseInterface.getAllUsers(): MutableList<UserInDB> =
    connection.use { connection ->
        connection
            .prepareStatement(
                """
                 SELECT * 
                 FROM users;
                """,
            )
            .use { preparedStatement ->
                return preparedStatement.executeQuery().toList { toUser() }
            }
    }

fun DatabaseInterface.getUserById(id: Int): UserInDB? =
    connection.use { connection ->
        connection
            .prepareStatement(
                """
                 SELECT * 
                 FROM users 
                 WHERE id=?;
                """,
            )
            .use { preparedStatement ->
                preparedStatement.setInt(1, id)
                return preparedStatement.executeQuery().toList { toUser() }.firstOrNull()
            }
    }

fun DatabaseInterface.updateUser(userRequest: UserRequest, userId: Int) =
    connection.use { connection ->
        connection
            .prepareStatement(
                """
              UPDATE users 
              SET name = ?, email = ? WHERE id = ?
                """,
            )
            .use { preparedStatement ->
                preparedStatement.setString(1, userRequest.name)
                preparedStatement.setString(2, userRequest.email)
                preparedStatement.setInt(3, userId)

                preparedStatement.executeQuery().next()
            }
    }

fun DatabaseInterface.deleteUser(id: Int) =
    connection.use { connection ->
        connection
            .prepareStatement(
                """
                DELETE FROM users
                WHERE id = ?;
                """,
            )
            .use { preparedStatement ->
                preparedStatement.setInt(1, id)
                preparedStatement.executeQuery().next()
            }
    }

fun ResultSet.toUser(): UserInDB =
    UserInDB(
        id = getInt("id"),
        name = getString("name"),
        email = getString("email"),
    )
