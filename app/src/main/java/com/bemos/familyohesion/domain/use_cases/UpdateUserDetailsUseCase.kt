package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class UpdateUserDetailsUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(
        userId: String,
        newEmail: String,
        newName: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
       return repository.updateUserDetails(
           userId = userId,
           newEmail = newEmail,
           newName = newName,
           onComplete = onComplete,
           onFailure = onFailure
       )
    }
}