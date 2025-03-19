package com.bemos.familyohesion.presentation.main_activity.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserIdUseCase
import com.bemos.familyohesion.presentation.main_activity.vm.MainViewModel

class MainViewModelFactory(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(
            getCurrentUserIdUseCase = getCurrentUserIdUseCase
        ) as T
    }
}