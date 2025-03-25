package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetFamilyIdForCurrentUserUseCase(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository
) {
    fun execute(
        callback: (String?) -> Unit
    ) {
        firebaseFirestoreRepository.getFamilyIdForCurrentUser(
            callback = callback
        )
    }
}