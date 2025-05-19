package com.bemos.familyohesion.presentation.features.finish_subSkill.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.CreateTaskUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateSubSkillRoomUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserPointsUseCase
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.FinishSubSkillViewModel

class FinishSubSkillViewModelFactory(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getFamilyMembersUseCase: GetFamilyMembersUseCase,
    private val updateSubSkillRoomUseCase: UpdateSubSkillRoomUseCase,
    private val updateUserPointsUseCase: UpdateUserPointsUseCase,
    private val getFamilyIdForCurrentUserUseCase: GetFamilyIdForCurrentUserUseCase,
    private val createTaskUseCase: CreateTaskUseCase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FinishSubSkillViewModel(
            getUserDataUseCase = getUserDataUseCase,
            getFamilyMembersUseCase = getFamilyMembersUseCase,
            updateSubSkillRoomUseCase = updateSubSkillRoomUseCase,
            updateUserPointsUseCase = updateUserPointsUseCase,
            getFamilyIdForCurrentUserUseCase = getFamilyIdForCurrentUserUseCase,
            createTaskUseCase = createTaskUseCase
        ) as T
    }
}