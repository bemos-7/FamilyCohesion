package com.bemos.familyohesion.presentation.features.edit_profile.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.use_cases.SendPasswordResetEmailUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EditProfileViewModel @Inject constructor(
    private val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
    private val sendPasswordResetEmailUseCase: SendPasswordResetEmailUseCase
) : ViewModel() {

    private val _onComplete = MutableStateFlow(false)
    val onComplete: StateFlow<Boolean> get() = _onComplete

    private val _onResetComplete = MutableStateFlow(false)
    val onResetComplete: StateFlow<Boolean> get() = _onResetComplete

    fun updateUserDetails(
        userId: String,
        newName: String,
        newEmail: String
    ) {
        updateUserDetailsUseCase.execute(
            userId = userId,
            newName = newName,
            newEmail = newEmail,
            onComplete = {
                _onComplete.update {
                    true
                }
            },
            onFailure = {

            }
        )
    }

    fun resetPassword(
        email: String
    ) {
        sendPasswordResetEmailUseCase.execute(
            email = email,
            onComplete = {
                _onResetComplete.update {
                    true
                }
            },
            onFailure = { e ->

            }
        )
    }

}