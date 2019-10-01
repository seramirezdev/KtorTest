package com.seramirezdev.entities

data class Place(
    val id: Int,
    val title: String,
    val description: String,
    val rating: Float,
    val latitude: Float,
    val longitude: Float,
    val location: String
)