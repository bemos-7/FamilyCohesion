package com.bemos.familyohesion.presentation.features.select_skill.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.presentation.features.select_skill.vm.SelectSkillViewModel

class SelectSkillViewModelFactory(
    private val getSkillsUseCase: GetSkillsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SelectSkillViewModel(
            getSkillsUseCase = getSkillsUseCase
        ) as T
    }
}