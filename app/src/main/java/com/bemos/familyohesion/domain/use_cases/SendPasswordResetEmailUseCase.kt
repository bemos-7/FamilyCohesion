package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository

class SendPasswordResetEmailUseCase(
    private val firebaseAuthRepository: FirebaseAuthRepository
) {
    fun execute(
        email: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firebaseAuthRepository.sendPasswordResetEmail(
            email = email,
            onComplete = onComplete,
            onFailure = onFailure
        )
    }
}