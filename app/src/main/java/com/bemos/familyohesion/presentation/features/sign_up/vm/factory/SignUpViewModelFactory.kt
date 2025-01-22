package com.bemos.familyohesion.presentation.features.sign_up.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import com.bemos.familyohesion.presentation.features.sign_up.vm.SignUpViewModel

class SignUpViewModelFactory(
    private val signUpUseCase: SignUpUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignUpViewModel(
            signUpUseCase = signUpUseCase
        ) as T
    }
}