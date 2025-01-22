package com.bemos.familyohesion.presentation.features.sign_in.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.presentation.features.sign_in.vm.SignInViewModel

class SignInViewModelFactory(
    private val signInUseCase: SignInUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SignInViewModel(
            signInUseCase = signInUseCase
        ) as T
    }
}