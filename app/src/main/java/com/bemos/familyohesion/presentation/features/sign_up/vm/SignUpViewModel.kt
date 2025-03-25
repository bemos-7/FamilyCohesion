package com.bemos.familyohesion.presentation.features.sign_up.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _onSuccess = MutableStateFlow(false)
    val onSuccess: StateFlow<Boolean> get() =_onSuccess

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    fun signUp(
        userAuth: UserAuth,
        familyName: String
    ) {
        signUpUseCase.execute(
            userAuth,
            familyName = familyName,
            onSuccess = {
                _onSuccess.update {
                    true
                }
            },
            onFailure = {
                _onFailure.update {
                    it
                }
            }
        )
    }
}