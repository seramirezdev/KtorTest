package com.seramirezdev.utils

object Utils {

    val PATH_IMAGES: String
        get() = "${getUserHomePath()}/images"

    const val PUBLIC_DIR_IMAGES: String = "public"

    private const val propertyUserHome: String = "user.home"
    private var userHomePath: String = ""

    private fun getUserHomePath(): String {
        if (userHomePath.isEmpty())
            userHomePath = System.getProperty(propertyUserHome)

        return userHomePath
    }
}