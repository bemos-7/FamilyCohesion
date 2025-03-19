package com.bemos.familyohesion.presentation.features.profile.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.use_cases.DeleteUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    private val _onComplete = MutableStateFlow(
        User(
            "","", "", "", ""
        )
    )
    val onComplete: StateFlow<User> get() = _onComplete

    private val _onDeleteComplete = MutableStateFlow(
        false
    )
    val onDeleteComplete: StateFlow<Boolean> get() = _onDeleteComplete

    private val _onResult = MutableStateFlow<List<FamilyMember>>(listOf())
    val onResult: StateFlow<List<FamilyMember>> get() = _onResult

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    init {
        getUserData()
    }

    fun getUserData() {
        getUserDataUseCase.execute(
            onComplete = { user ->
                _onComplete.update {
                    user
                }
                getFamilyMembers(
                    user.familyId,
                    onResult = { members ->
                        _onResult.update {
                            members
                        }
                    }
                )
            },
            onFailure = { e ->
                _onFailure.update {
                    e
                }
            }
        )
    }

    private fun getFamilyMembers(
        familyId: String,
        onResult: (List<FamilyMember>) -> Unit
    ) {
        getFamilyMembersUseCase.execute(
            familyId = familyId,
            onResult = onResult
        )
    }

    fun deleteUser(
        userId: String
    ) {
        deleteUserUseCase.execute(
            userId = userId,
            onComplete = {
                _onDeleteComplete.update {
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