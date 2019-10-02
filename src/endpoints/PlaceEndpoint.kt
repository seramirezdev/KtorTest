package com.seramirezdev.endpoints

import com.seramirezdev.entities.Image
import com.seramirezdev.entities.Place
import com.seramirezdev.repositories.PlaceRepository
import com.seramirezdev.responses.Message
import com.seramirezdev.security.authenticateUser
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.MultiPartData
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.*
import kotlinx.coroutines.runBlocking
import java.io.File

const val PATH_IMAGES = "/home/sergio/Desarrollo/SpringBoot/images"
const val PUBLIC_DIR_IMAGE = "public"

fun Route.placeEndpoint(placeRepository: PlaceRepository) {
    route("places") {

        contentType(ContentType.MultiPart.FormData) {
            post("create") {
                call.authenticateUser!!

                val params = call.receiveMultipart()
                val place = getParamsToPlace(params)
                val createPlace = placeRepository.insertPlaceAndImages(place)

                if (createPlace == null) {
                    call.respond(HttpStatusCode.NotAcceptable, Message("No se pudo crear el Lugar"))
                } else {
                    call.respond(HttpStatusCode.OK, createPlace)
                }
            }
        }

        get {
            call.authenticateUser!!
            call.respond(HttpStatusCode.OK, placeRepository.getAllPlaces())
        }
    }
}

private fun getParamsToPlace(params: MultiPartData): Place {
    val place = Place()
    val imagesList = mutableListOf<Image>()

    runBlocking {
        params.forEachPart { part ->
            when (part) {
                is PartData.FormItem -> {
                    when (part.name) {
                        "title" -> place.title = part.value
                        "description" -> place.description = part.value
                        "rating" -> place.rating = part.value.toFloat()
                        "latitude" -> place.latitude = part.value.toFloat()
                        "longitude" -> place.longitude = part.value.toFloat()
                        "location" -> place.location = part.value
                    }
                }
                is PartData.FileItem -> {
                    val root = "$PATH_IMAGES/$PUBLIC_DIR_IMAGE"
                    val extension = File(part.originalFileName!!).extension
                    val fileName = "${System.currentTimeMillis()}.$extension"
                    print(fileName)

                    val file = File(root, fileName)
                    part.streamProvider()
                        .use { input ->
                            file.outputStream().buffered().use { output -> input.copyTo(output) }
                        }
                    val image = Image(path = fileName)
                    println(image)
                    imagesList.add(image)
                }
            }
            part.dispose()
        }
        place.images = imagesList
    }

    return place
}