package com.bemos.familyohesion.presentation.features.sign_in.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _onSuccess = MutableStateFlow(false)
    val onSuccess: StateFlow<Boolean> get() =_onSuccess

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    fun signIn(userAuth: UserAuth) {
        signInUseCase.execute(
            userAuth,
            onSuccess = {
                _onSuccess.update {
                    true
                }
            },
            onFailure = { exception ->
                _onFailure.update {
                    exception
                }
            }
        )
    }
}