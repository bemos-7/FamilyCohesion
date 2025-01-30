package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetSkillsUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        onComplete: (Map<String, List<Skill>>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.getSkills(
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}