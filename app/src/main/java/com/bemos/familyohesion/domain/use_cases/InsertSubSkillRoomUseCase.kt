package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.data.mappers.SubSkillMapper.toEntity
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.repositories.DaoRepository

class InsertSubSkillRoomUseCase(
    private val daoRepository: DaoRepository
) {
    suspend fun execute(subSkill: SubSkill) {
        daoRepository.insertSubSkill(subSkill = subSkill.toEntity())
    }
}