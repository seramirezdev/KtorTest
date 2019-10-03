package com.seramirezdev.endpoints

import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.routing.Route
import java.io.File

fun Route.imagesEndpoint() {
    static("images") {
        staticRootFolder = File(PATH_IMAGES)
        files(PUBLIC_DIR_IMAGE)
    }
}