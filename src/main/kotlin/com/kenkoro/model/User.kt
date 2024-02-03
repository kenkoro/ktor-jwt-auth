package com.kenkoro.model

import com.kenkoro.model.util.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
  val id: Int = -1,
  val subject: String,
  val password: String,
  val image: String,
  @SerialName("first_name") val firstName: String,
  @SerialName("last_name") val lastName: String,
  val role: UserRole,
  val token: String = ""
)
