package com.bemos.familyohesion.presentation.features.skills.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import com.bemos.familyohesion.presentation.features.skills.vm.SkillsViewModel
import javax.inject.Inject

class SkillsViewModelFactory(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSkillsUseCase: GetSkillsUseCase,
    private val getSubSkillsUseCase: GetSubSkillsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SkillsViewModel(
            getCategoriesUseCase = getCategoriesUseCase,
            getSkillsUseCase = getSkillsUseCase,
            getSubSkillsUseCase =  getSubSkillsUseCase
        ) as T
    }
}