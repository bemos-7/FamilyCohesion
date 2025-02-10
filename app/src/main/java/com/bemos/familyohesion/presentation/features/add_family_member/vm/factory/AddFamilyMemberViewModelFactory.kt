package com.bemos.familyohesion.presentation.features.add_family_member.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.SignUpAndJoinFamilyUseCase
import com.bemos.familyohesion.presentation.features.add_family_member.vm.AddFamilyMemberViewModel

class AddFamilyMemberViewModelFactory(
    private val signUpAndJoinFamilyUseCase: SignUpAndJoinFamilyUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddFamilyMemberViewModel(
            signUpAndJoinFamilyUseCase = signUpAndJoinFamilyUseCase
        ) as T
    }
}