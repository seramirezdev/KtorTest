package com.seramirezdev

import com.seramirezdev.config.DatabaseFactory
import com.seramirezdev.config.JWTConfig
import com.seramirezdev.endpoints.*
import com.seramirezdev.repositories.CommentRepository
import com.seramirezdev.repositories.PlaceRepository
import com.seramirezdev.repositories.UserRepository
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.gson.gson
import io.ktor.routing.Routing

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()

    install(CallLogging)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CORS) {
        anyHost()
    }

    val userRepository = UserRepository()
    val placesRepository = PlaceRepository()
    val commentRepository = CommentRepository()

    install(Authentication) {
        jwt {
            verifier(JWTConfig.verifier)
            realm = JWTConfig.issuer
            validate {

                it.payload.getClaim("username").asString()?.let(userRepository::findUserByUsername)
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {

        auth(userRepository)

        authenticate {
            placeEndpoint(placesRepository)
            userEndpoint(userRepository)
            commentEndpoint(commentRepository)
            imagesEndpoint()

        }
    }
}
