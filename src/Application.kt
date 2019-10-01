package com.seramirezdev

import com.seramirezdev.config.DatabaseFactory
import com.seramirezdev.config.JWTConfig
import com.seramirezdev.endpoints.auth
import com.seramirezdev.repositories.UserRepository
import com.seramirezdev.security.authenticateUser
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.authenticate
import io.ktor.auth.jwt.jwt
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    DatabaseFactory.init()

    install(CallLogging)
    install(DefaultHeaders)
    install(CORS) {
        anyHost()
    }

    val userRepository = UserRepository()

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
            route("users") {
                get {
                    call.authenticateUser!!
                    call.respond(HttpStatusCode.OK, userRepository.getAllUsers())
                }
            }
        }
    }
}

