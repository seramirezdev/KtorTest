package com.seramirezdev.dto

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Favorites : IntIdTable() {
    val user = Favorites.reference("user", Users)
    val place = Favorites.reference("place", Places)


}

class FavoritesDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FavoritesDAO>(Favorites)

    var user by UserDAO referencedOn Favorites.user
    var place by PlaceDAO referencedOn Favorites.place

}