package com.kenkoro.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
  val jwtAudience = environment.config.property("jwt.audience").getString()
  val jwtDomain = environment.config.property("jwt.issuer").getString()
  val jwtRealm = environment.config.property("jwt.realm").getString()
  val jwtSecret = environment.config.property("jwt.secret").getString()

  install(Authentication) {
    jwt("jwt.auth") {
      realm = jwtRealm
      verifier(
        JWT
          .require(Algorithm.HMAC256(jwtSecret))
          .withAudience(jwtAudience)
          .withIssuer(jwtDomain)
          .build()
      )

      validate { credential ->
        if (credential.payload.audience.contains(jwtAudience)) {
          JWTPrincipal(credential.payload)
        } else {
          null
        }
      }

      challenge { defaultScheme, realm ->
        call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired: $defaultScheme")
      }
    }
  }
}
