package com.bemos.familyohesion.presentation.features.edit_profile.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.SendPasswordResetEmailUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserDetailsUseCase
import com.bemos.familyohesion.presentation.features.edit_profile.vm.EditProfileViewModel

class EditProfileViewModelFactory(
    private val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EditProfileViewModel(
            updateUserDetailsUseCase = updateUserDetailsUseCase,
            sendPasswordResetEmailUseCase = sendPasswordResetEmailUseCase
        ) as T
    }
}