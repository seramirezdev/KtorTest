package com.seramirezdev.entities

import io.ktor.auth.Principal

data class User(val id: Int, val name: String, val username: String, var password: String? = null) : Principal