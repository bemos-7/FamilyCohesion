package com.bemos.familyohesion.data.mappers

import com.bemos.familyohesion.data.local.entities.SkillEntity
import com.bemos.familyohesion.domain.models.Skill

object SkillMapper {
    fun Skill.toEntity() : SkillEntity {
        return SkillEntity(
            id,
            name,
            categoryId
        )
    }

    fun SkillEntity.toSkill() : Skill {
        return Skill(
            id,
            name,
            categoryId
        )
    }
}