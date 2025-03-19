package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class DeleteUserUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        userId: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        return repository.deleteUser(
            userId = userId,
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}