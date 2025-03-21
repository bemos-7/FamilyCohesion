package com.bemos.familyohesion.presentation.features.finish_subSkill.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateSubSkillRoomUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FinishSubSkillViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase,
    private val updateSubSkillRoomUseCase: UpdateSubSkillRoomUseCase
) : ViewModel() {

    private val _onComplete = MutableStateFlow(
        User(
            "","", "", "", ""
        )
    )
    val onComplete: StateFlow<User> get() = _onComplete

    private val _onResult = MutableStateFlow<List<FamilyMember>>(listOf())
    val onResult: StateFlow<List<FamilyMember>> get() = _onResult

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    init {
        getUserData()
    }

    private fun getUserData() {
        getUserDataUseCase.execute(
            onComplete = { user ->
                _onComplete.update {
                    user
                }
                getFamilyMembers(
                    user.familyId,
                    onResult = { members ->
                        removingCurrentUser(members)
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

    private fun removingCurrentUser(
        familyMembers: List<FamilyMember>
    ) {
        val familyMembersList = mutableListOf<FamilyMember>()
        familyMembers.forEach { member ->
            if (member.name != _onComplete.value.name && member.relation != _onComplete.value.role) {
                familyMembersList.add(member)
            }
        }
        _onResult.update {
            familyMembersList
        }
    }

    fun updateSubSkillInRoom(subSkill: SubSkill) = viewModelScope.launch {
        updateSubSkillRoomUseCase.execute(subSkill)
    }
}