package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.repositories.DaoRepository
import kotlinx.coroutines.flow.Flow

class GetAllSubSkillsRoomUseCase(
    private val daoRepository: DaoRepository
) {
    fun execute(): Flow<List<SubSkill>> {
        return daoRepository.getAllSubSkills()
    }
}