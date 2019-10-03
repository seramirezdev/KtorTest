package com.seramirezdev.dto

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Places : IntIdTable() {
    val title = varchar("title", length = 30)
    val description = varchar("description", length = 50)
    val rating = float("rating")
    val latitude = float("latitude")
    val longitude = float("longitude")
    val location = varchar("location", length = 50)
}

class PlaceDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlaceDAO>(Places)

    var title by Places.title
    var description by Places.description
    var rating by Places.rating
    var latitude by Places.latitude
    var longitude by Places.longitude
    var location by Places.location
    val images by ImageDAO referrersOn Images.place
    val comments by CommentDAO referrersOn Comments.place
}

