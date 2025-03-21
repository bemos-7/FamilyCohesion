package com.bemos.familyohesion.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bemos.familyohesion.data.local.entities.CategoryEntity
import com.bemos.familyohesion.data.local.entities.SkillEntity
import com.bemos.familyohesion.data.local.entities.SubSkillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("select * from categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}

@Dao
interface SkillDao {
    @Query("select * from skills")
    fun getAllSkills(): Flow<List<SkillEntity>>
}

@Dao
interface SubSkillDao {
    @Query("select * from subSkills")
    fun getAllSubSkills(): Flow<List<SubSkillEntity>>

    @Update
    suspend fun update(subSkill: SubSkillEntity)

    @Insert
    suspend fun insertSubSkill(subSkill: SubSkillEntity)
}