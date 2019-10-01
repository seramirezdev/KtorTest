package com.seramirezdev.endpoints

import com.seramirezdev.config.JWTConfig
import com.seramirezdev.repositories.UserRepository
import com.seramirezdev.responses.Message
import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post

fun Route.auth(userRepository: UserRepository) {

    post("login") {

        val credentials = call.receive<UserPasswordCredential>()
        val user = userRepository.findUserByCredentials(credentials)

        if (user == null) {
            call.respond(HttpStatusCode.NotFound, Message(message = "Credenciales inválidas"))
        } else {
            val token = JWTConfig.makeToken(user)
            call.respond(HttpStatusCode.OK, mapOf("jwtKey" to token))
        }
    }

    post("register") {

    }
}