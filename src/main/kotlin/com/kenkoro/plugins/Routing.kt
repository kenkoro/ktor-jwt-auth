package com.kenkoro.plugins

import com.kenkoro.route.getAllUsers
import com.kenkoro.route.signUp
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Application.configureRouting(db: Connection) {
  routing {
    getAllUsers(db)
    signUp(db)
  }
}
