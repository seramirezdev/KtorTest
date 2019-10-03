package com.seramirezdev.repositories

import com.seramirezdev.dto.UserDAO
import com.seramirezdev.dto.Users
import com.seramirezdev.entities.User
import io.ktor.auth.UserPasswordCredential
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction


class UserRepository {

    fun findUserByCredentials(credentials: UserPasswordCredential): User? =
        transaction {
            UserDAO.find {
                (Users.username eq credentials.name) and (Users.password eq credentials.password)
            }.map { toUser(it) }.firstOrNull()
        }


    fun findUserByUsername(username: String): User =
        transaction {
            UserDAO.find {
                Users.username eq username
            }.map { toUser(it) }.first()
        }


    fun getAllUsers(): List<User> = transaction {
        UserDAO.all().map { toUser(it) }
    }

    fun insertUser(user: User): User? {
        var createdUser: User? = null

        transaction {
            val userDAO = UserDAO.new {
                name = user.name
                username = user.username
                password = user.password!!
            }

            createdUser = toUser(userDAO)
        }

        return createdUser
    }

    companion object {
        fun toUser(user: UserDAO) = User(
            id = user.id.value,
            username = user.username,
            name = user.name
        )
    }
}
