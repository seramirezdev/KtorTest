package com.seramirezdev.entities

data class Place(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var rating: Float = 0.0F,
    var latitude: Float = 0.0F,
    var longitude: Float = 0.0F,
    var location: String = "",
    var comments: List<Comment> = emptyList(),
    var images: List<Image> = emptyList()
)