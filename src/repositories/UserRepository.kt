package com.seramirezdev.repositories

import com.seramirezdev.dto.FavoritesDAO
import com.seramirezdev.dto.UserDAO
import com.seramirezdev.dto.Users
import com.seramirezdev.entities.Favorite
import com.seramirezdev.entities.User
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


class UserRepository {

    fun findUserByCredentials(credentials: User): User? =
        transaction {
            val user = UserDAO.find {
                (Users.username eq credentials.username) and (Users.password eq credentials.password!!)
            }

            if (!user.empty())
                Users.update({ Users.id eq user.first().id }) { it[fcmToken] = credentials.fcmToken }

            user.map { toUser(it) }.firstOrNull()
        }


    fun findUserByUsername(username: String): User =
        transaction {
            UserDAO.find {
                Users.username eq username
            }.map { toUser(it) }.first()
        }

    fun validateUserIsAdmin(username: String): User =
        transaction {
            UserDAO.find {
                (Users.username eq username) and (Users.role eq "admin")
            }.map { toUser(it) }.first()
        }


    fun getAllUsers(): List<User> = transaction {
        UserDAO.all().map { toUser(it) }
    }

    fun getFavoritesByUSer(userId : Int): List<Favorite> = transaction {
        UserDAO.findById(userId)!!.favorites.map { toFavorite(it) }
    }

    fun insertUser(user: User): User? {
        var createdUser: User? = null

        transaction {
            val userDAO = UserDAO.new {
                name = user.name
                username = user.username
                password = user.password!!
                fcmToken = user.fcmToken
            }

            createdUser = toUser(userDAO)
        }

        return createdUser
    }

    companion object {
        fun toUser(user: UserDAO) = User(
            id = user.id.value,
            username = user.username,
            name = user.name,
            fcmToken = user.fcmToken ?: ""
        )

        fun toFavorite(favorites: FavoritesDAO) : Favorite = Favorite(
            id = favorites.id.value,
            place = PlaceRepository.toPlace(favorites.place),
            user = UserRepository.toUser(favorites.user)
        )
    }
}
