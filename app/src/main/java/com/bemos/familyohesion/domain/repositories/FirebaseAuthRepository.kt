package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.domain.models.UserAuth

interface FirebaseAuthRepository {
    fun getCurrentUserId() : String?
    fun signUp(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signUpAndJoinFamily(userAuth: UserAuth, familyId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signIn(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun sendPasswordResetEmail(email: String, onComplete: () -> Unit, onFailure: (Exception) -> Unit)
}