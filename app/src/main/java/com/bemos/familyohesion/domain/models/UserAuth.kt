package com.bemos.familyohesion.domain.models

data class UserAuth(
    val name: String,
    val email: String,
    val password: String,
    val userRole: String
)