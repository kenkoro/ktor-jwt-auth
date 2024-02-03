package com.kenkoro

import com.kenkoro.plugins.configureDatabasesAndRouting
import com.kenkoro.plugins.configureRouting
import com.kenkoro.plugins.configureSecurity
import com.kenkoro.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
  configureSecurity()
  configureSerialization()
  configureDatabasesAndRouting()
}
