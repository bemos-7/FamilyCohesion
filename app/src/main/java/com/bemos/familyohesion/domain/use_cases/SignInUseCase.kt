package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository

class SignInUseCase(
    private val repository: FirebaseAuthRepository
) {
    fun execute(
        userAuth: UserAuth,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.signIn(
            userAuth = userAuth,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}