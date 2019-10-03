package com.seramirezdev.endpoints

import com.seramirezdev.repositories.UserRepository
import com.seramirezdev.security.authenticateUser
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

fun Route.userEndpoint(userRepository: UserRepository) {
    route("users") {
        get {
            call.authenticateUser!!
            call.respond(HttpStatusCode.OK, userRepository.getAllUsers())
        }
    }
}