package com.bemos.familyohesion.domain.models

import kotlinx.serialization.Serializable

data class Family(
    val familyId: String = "",
    val name: String = "",
    val adminId: String = "",
    val members: List<Map<String, Any>> = emptyList(),
    val familyPoints: Int = 0
)