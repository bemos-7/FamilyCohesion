package com.bemos.familyohesion.presentation.features.profile.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.presentation.features.profile.vm.ProfileViewModel

class ProfileViewModelFactory(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(
            getUserDataUseCase = getUserDataUseCase,
            getFamilyMembersUseCase = getFamilyMembersUseCase
        ) as T
    }
}