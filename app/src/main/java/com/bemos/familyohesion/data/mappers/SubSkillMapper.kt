package com.bemos.familyohesion.data.mappers

import com.bemos.familyohesion.data.local.entities.SubSkillEntity
import com.bemos.familyohesion.domain.models.SubSkill

object SubSkillMapper {
    fun SubSkill.toEntity() : SubSkillEntity {
        return SubSkillEntity(
            id,
            name,
            points,
            skillId,
            isCompleted
        )
    }

    fun SubSkillEntity.toSubSkill() : SubSkill {
        return SubSkill(
            id,
            name,
            points,
            skillId,
            isCompleted
        )
    }
}