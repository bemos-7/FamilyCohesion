package com.bemos.familyohesion.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

data class Category(
    val id: String = "",
    val name: String = ""
)

data class Skill(
    val id: String = "",
    val name: String = "",
    val categoryId: String = "",
    val subSkills: List<SubSkill>? = null
)

@Serializable
data class SubSkill(
    val id: String = "",
    val name: String = "",
    val points: Int = 0,
    val skillId: String = "",
    val isCompleted: Boolean = false
)