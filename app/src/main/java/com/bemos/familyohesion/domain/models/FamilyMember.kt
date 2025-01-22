package com.bemos.familyohesion.domain.models

data class FamilyMember(
    val name: String,
    val age: Int,
    val relation: String,
    val points: Double,
    val familyId: String
)