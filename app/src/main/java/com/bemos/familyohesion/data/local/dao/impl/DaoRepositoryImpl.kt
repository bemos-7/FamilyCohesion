package com.bemos.familyohesion.data.local.dao.impl

import com.bemos.familyohesion.data.local.dao.CategoryDao
import com.bemos.familyohesion.data.local.dao.SkillDao
import com.bemos.familyohesion.data.local.dao.SubSkillDao
import com.bemos.familyohesion.data.local.entities.SubSkillEntity
import com.bemos.familyohesion.data.mappers.CategoryMapper.toCategory
import com.bemos.familyohesion.data.mappers.SkillMapper.toSkill
import com.bemos.familyohesion.data.mappers.SubSkillMapper.toSubSkill
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.repositories.DaoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DaoRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val skillDao: SkillDao,
    private val subSkillDao: SubSkillDao
) : DaoRepository {
    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
            .map { categoryList ->
                categoryList.map { categoryEntity ->
                    categoryEntity.toCategory()
                }
            }
    }

    override fun getAllSkills(): Flow<List<Skill>> {
        return skillDao.getAllSkills()
            .map { skillList ->
                skillList.map { skillEntity ->
                    skillEntity.toSkill()
                }
            }
    }

    override fun getAllSubSkills(): Flow<List<SubSkill>> {
        return subSkillDao.getAllSubSkills()
            .map { subSkillList ->
                subSkillList.map { subSkillEntity ->
                    subSkillEntity.toSubSkill()
                }
            }
    }

    override suspend fun updateSubSkill(subSkill: SubSkillEntity) {
        subSkillDao.update(subSkill)
    }

    override suspend fun insertSubSkill(subSkill: SubSkillEntity) {
        subSkillDao.insertSubSkill(subSkill)
    }

}