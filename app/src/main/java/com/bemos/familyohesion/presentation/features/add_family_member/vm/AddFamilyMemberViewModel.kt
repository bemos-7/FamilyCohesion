package com.bemos.familyohesion.presentation.features.add_family_member.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.use_cases.SignUpAndJoinFamilyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AddFamilyMemberViewModel @Inject constructor(
    private val signUpAndJoinFamilyUseCase: SignUpAndJoinFamilyUseCase
) : ViewModel() {

    private val _onSuccess = MutableStateFlow(false)
    val onSuccess: StateFlow<Boolean> get() =_onSuccess

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    fun signUpAndJoinFamily(
        userAuth: UserAuth,
        familyId: String
    ) {
        signUpAndJoinFamilyUseCase.execute(
            userAuth = userAuth,
            familyId = familyId,
            onSuccess = {
                _onSuccess.update {
                    true
                }
            },
            onFailure = { e ->
                _onFailure.update {
                    e
                }
            }
        )
    }
}