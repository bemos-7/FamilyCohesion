package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetSubSkillsUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        skillId: String, onComplete: (List<SubSkill>) -> Unit, onFailure: (Exception) -> Unit
    ) {
        repository.getSubSkills(
            skillId = skillId,
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}