package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository

class GetCurrentUserIdUseCase(
    private val repository: FirebaseAuthRepository
) {
    fun execute() : String? {
        return repository.getCurrentUserId()
    }
}