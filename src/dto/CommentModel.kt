package com.seramirezdev.dto

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Comments : IntIdTable() {
    val comment = text("comment")
    val rating = float("rating")
    val user = reference("user", Users)
    val place = reference("place", Places)
}

class CommentDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CommentDAO>(Comments)

    var comment by Comments.comment
    var rating by Comments.rating
    var user by UserDAO referencedOn Comments.user
    var place by PlaceDAO referencedOn Comments.place
}