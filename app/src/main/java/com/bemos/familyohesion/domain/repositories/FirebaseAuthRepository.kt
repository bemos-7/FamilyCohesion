package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.domain.models.UserAuth

interface FirebaseAuthRepository {
    fun signUp(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun signIn(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
}