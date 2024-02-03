package com.kenkoro.route

import com.kenkoro.model.User
import com.kenkoro.model.util.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection

fun Route.getAllUsers(db: Connection) {
  authenticate("jwt.auth") {
    get("/api/users") {
      val result = db
        .createStatement()
        .executeQuery("select * from t_user")

      val users = mutableListOf<User>()
      while (result.next()) {
        users += User(
          result.getInt(1),
          result.getString(2),
          result.getString(3),
          result.getString(4),
          result.getString(5),
          result.getString(6),
          UserRole.valueOf(result.getString(7).uppercase()),
          result.getString(8)
        )
      }

      if (users.isNotEmpty()) {
        call.respond(HttpStatusCode.Found, users)
      } else {
        call.respondText(
          "Not found",
          status = HttpStatusCode.NotFound
        )
      }
    }
  }
}