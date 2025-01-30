package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetUserDataUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(onComplete: (UserAuth) -> Unit, onFailure: (Exception) -> Unit) {
        repository.getUserData(
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}