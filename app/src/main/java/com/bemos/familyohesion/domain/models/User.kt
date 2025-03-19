package com.bemos.familyohesion.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String = "",
    val familyId: String = "",
    val name: String = "",
    val role: String = "",
    val userId: String = ""
)