package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetSkillsUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        categoryId: String, onComplete: (List<Skill>) -> Unit, onFailure: (Exception) -> Unit
    ) {
        repository.getSkills(
            categoryId = categoryId,
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}