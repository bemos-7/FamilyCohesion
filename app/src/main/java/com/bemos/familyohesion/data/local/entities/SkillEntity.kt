package com.bemos.familyohesion.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String
)

@Entity(tableName = "skills")
data class SkillEntity(
    @PrimaryKey val id: String,
    val name: String,
    val categoryId: String,
)

@Entity(tableName = "subSkills")
data class SubSkillEntity(
    @PrimaryKey val id: String,
    val name: String,
    val points: Int = 0,
    val skillId: String,
    var isCompleted: Boolean = false
)