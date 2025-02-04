package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetUserDataUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(onComplete: (User) -> Unit, onFailure: (Exception) -> Unit) {
        repository.getUserData(
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}