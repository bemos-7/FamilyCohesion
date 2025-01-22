package com.bemos.familyohesion.domain.models

data class Family(
    val familyId: String,
    val adminId: String,
    val members: List<Map<String, Any>> = emptyList(),
    val familyPoints: Int = 0
)