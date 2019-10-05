package com.seramirezdev.dto

import com.seramirezdev.dto.PlaceDAO.Companion.referrersOn
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", length = 30)
    val role = varchar("role", length = 30).default("user")
    val username = varchar("username", length = 50).uniqueIndex()
    val password = varchar("password", length = 50)
    val fcmToken = text("fcm_token").nullable()
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(Users)

    var name by Users.name
    var role by Users.role
    var username by Users.username
    var password by Users.password
    var fcmToken by Users.fcmToken
    val favorites by FavoritesDAO referrersOn Favorites.user
}