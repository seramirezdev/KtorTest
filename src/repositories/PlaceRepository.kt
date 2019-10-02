package com.seramirezdev.repositories

import com.seramirezdev.dto.CommentDAO
import com.seramirezdev.dto.ImageDAO
import com.seramirezdev.dto.PlaceDAO
import com.seramirezdev.entities.Comment
import com.seramirezdev.entities.Image
import com.seramirezdev.entities.Place
import org.jetbrains.exposed.sql.transactions.transaction

class PlaceRepository {

    fun getAllPlaces(): List<Place> = transaction {
        PlaceDAO.all().map { toPlace(it) }
    }

    fun insertPlaceAndImages(place: Place): Place? {
        var createdPlace: Place? = null

        val insertPlace = transaction {
            PlaceDAO.new {
                title = place.title
                description = place.description
                rating = place.rating
                latitude = place.latitude
                longitude = place.longitude
                location = place.location
            }
        }

        transaction {
            place.images.forEachIndexed { index, image ->
                ImageDAO.new {
                    this.path = image.path
                    this.place = insertPlace
                }
            }

            createdPlace = toPlace(insertPlace)
        }

        return createdPlace
    }

    companion object {
        fun toPlace(place: PlaceDAO): Place = Place(
            id = place.id.value,
            title = place.title,
            description = place.description,
            rating = place.rating,
            location = place.location,
            latitude = place.latitude,
            longitude = place.longitude,
            comments = place.comments.map { toComment(it) },
            images = place.images.map { toImages(it) }
        )

        fun toComment(comment: CommentDAO): Comment =
            Comment(
                id = comment.id.value,
                comment = comment.comment,
                rating = comment.rating,
                place = null,
                user = UserRepository.toUser(comment.user)
            )

        fun toImages(image: ImageDAO): Image =
            Image(
                id = image.id.value,
                path = image.path,
                place = null
            )
    }
}