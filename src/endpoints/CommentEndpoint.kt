package com.seramirezdev.endpoints

import com.seramirezdev.entities.Comment
import com.seramirezdev.repositories.CommentRepository
import com.seramirezdev.responses.Message
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.commentEndpoint(commentReposiroty: CommentRepository) {
    route("comments") {
        post("create/{userId}/{placeId}") {
            val userId =
                call.parameters["userId"]?.toInt() ?: throw IllegalStateException("Debe enviar el ID del usuario")
            val placeId =
                call.parameters["placeId"]?.toInt() ?: throw IllegalStateException("Debe enviar el ID del Lugar")

            val comment = call.receive<Comment>()
            val createdComment = commentReposiroty.insertCommentToPlace(placeId, userId, comment)

            if (createdComment == null) {
                call.respond(HttpStatusCode.NotAcceptable, Message(message = "No se pudo crear el comentario"))
            } else {
                call.respond(HttpStatusCode.OK, createdComment)
            }
        }
    }
}