package com.bemos.familyohesion.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class FamilyMember(
    val name: String = "",
    val relation: String = "",
    val points: Double = 0.0,
    val userId: String = ""
)