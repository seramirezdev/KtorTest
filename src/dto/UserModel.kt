package com.seramirezdev.dto

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", length = 30)
    val username = varchar("username", length = 50)
    val password = varchar("password", length = 50)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(Users)

    var name by Users.name
    var username by Users.username
    var password by Users.password
}