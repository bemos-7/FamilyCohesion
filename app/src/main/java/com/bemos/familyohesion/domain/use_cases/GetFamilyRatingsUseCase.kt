package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository
import com.bemos.familyohesion.presentation.features.rating.utils.model.FamilyRating

class GetFamilyRatingsUseCase(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository
) {
    fun execute(callback: (List<FamilyRating>) -> Unit) {
        firebaseFirestoreRepository.getFamilyRatings(
            callback = callback
        )
    }
}