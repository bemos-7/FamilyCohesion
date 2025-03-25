package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class UpdateUserPointsUseCase(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository
) {
    fun execute(
        familyId: String,
        pointsToAdd: Int
    ) {
        firebaseFirestoreRepository.updateUserPoints(
            familyId,
            pointsToAdd
        )
    }
}