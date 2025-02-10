package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository

class SignUpAndJoinFamilyUseCase(
    private val repository: FirebaseAuthRepository
) {
    fun execute(
        userAuth: UserAuth,
        familyId: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        repository.signUpAndJoinFamily(
            userAuth = userAuth,
            familyId = familyId,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}