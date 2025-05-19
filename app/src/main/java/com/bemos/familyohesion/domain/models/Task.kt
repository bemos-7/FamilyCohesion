package com.bemos.familyohesion.domain.models

data class Task(
    val id: String = "",
    val imageUrl: String = "",
    val familyId: String = "",
    val userId: String = "",
    val pointsToAdd: Int = 0,
    val name: String = "",
    val isApproved: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)