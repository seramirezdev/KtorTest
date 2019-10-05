package com.seramirezdev.endpoints

import com.seramirezdev.config.JWTConfig
import com.seramirezdev.entities.User
import com.seramirezdev.repositories.UserRepository
import com.seramirezdev.responses.Message
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.auth(userRepository: UserRepository) {

    post("login") {

        val credentials = call.receive<User>()
        val user = userRepository.findUserByCredentials(credentials)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, Message(message = "Credenciales inválidas"))
        } else {
            val token = JWTConfig.makeToken(user)
            call.respond(HttpStatusCode.OK, mapOf("jwtKey" to token))
        }
    }

    post("register") {
        val user = call.receive<User>()
        val createdUser = userRepository.insertUser(user)

        if (createdUser == null) {
            call.respond(HttpStatusCode.NotAcceptable, Message(message = "No se pudo crear el usuario"))
        } else {
            val token = JWTConfig.makeToken(createdUser)
            call.respond(HttpStatusCode.OK, mapOf("jwtKey" to token))
        }
    }
}