package com.seramirezdev.entities

data class Comment(val id: Int, val comment: String, val rating: Float, val user: User?, val place: Place?)