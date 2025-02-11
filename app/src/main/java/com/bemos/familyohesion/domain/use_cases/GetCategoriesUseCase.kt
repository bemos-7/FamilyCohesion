package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetCategoriesUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        onComplete: (List<Category>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.getCategories(
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}