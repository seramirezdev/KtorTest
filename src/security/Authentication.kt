package com.seramirezdev.security

import com.seramirezdev.dto.UserDAO
import com.seramirezdev.entities.User
import io.ktor.application.ApplicationCall
import io.ktor.auth.authentication

val ApplicationCall.authenticateUser get() = authentication.principal<User>()