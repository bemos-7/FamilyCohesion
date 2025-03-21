package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.data.local.entities.SubSkillEntity
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import kotlinx.coroutines.flow.Flow

interface DaoRepository {
    fun getAllCategories(): Flow<List<Category>>

    fun getAllSkills(): Flow<List<Skill>>

    fun getAllSubSkills(): Flow<List<SubSkill>>

    suspend fun updateSubSkill(subSkill: SubSkillEntity)

    suspend fun insertSubSkill(subSkill: SubSkillEntity)
}