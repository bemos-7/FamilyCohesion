package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository

class SignUpUseCase(
    private val repository: FirebaseAuthRepository
) {
    fun execute(
        userAuth: UserAuth,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.signUp(
            userAuth = userAuth,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}