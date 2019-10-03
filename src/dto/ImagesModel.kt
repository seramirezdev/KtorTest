package com.seramirezdev.dto

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntIdTable

object Images : IntIdTable() {
    val path = varchar("path", length = 100)
    val place = reference("place", Places)
}

class ImageDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ImageDAO>(Images)

    var path by Images.path
    var place by PlaceDAO referencedOn Images.place
}