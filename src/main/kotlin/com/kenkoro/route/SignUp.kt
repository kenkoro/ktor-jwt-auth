package com.kenkoro.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.kenkoro.model.User
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.Date

fun Route.signUp(db: Connection) {
  val jwtAudience = environment!!.config.property("jwt.audience").getString()
  val jwtDomain = environment!!.config.property("jwt.issuer").getString()
  val jwtSecret = environment!!.config.property("jwt.secret").getString()

  post("/api/signup") {
    val user = call.receive<User>();
    val token = JWT.create()
      .withAudience(jwtAudience)
      .withIssuer(jwtDomain)
      .withClaim("sub", user.subject)
      .withClaim("nbf", System.currentTimeMillis())
      .withExpiresAt(Date(System.currentTimeMillis() + 60000))
      .sign(Algorithm.HMAC256(jwtSecret))

    // Here, you insert a new user w/ a token and a hashed password

    call.respond(hashMapOf("token" to token))
  }
}