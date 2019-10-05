package com.seramirezdev.endpoints

import com.seramirezdev.utils.Utils
import io.ktor.http.content.files
import io.ktor.http.content.static
import io.ktor.http.content.staticRootFolder
import io.ktor.routing.Route
import java.io.File

fun Route.imagesEndpoint() {
    static("images") {
        staticRootFolder = File(Utils.PATH_IMAGES)
        files(Utils.PUBLIC_DIR_IMAGES)
    }
}