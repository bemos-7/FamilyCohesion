package com.bemos.familyohesion.presentation.features.skills.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SkillsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSkillsUseCase: GetSkillsUseCase,
    private val getSubSkillsUseCase: GetSubSkillsUseCase
): ViewModel() {
    private val _onCategoryComplete = MutableStateFlow<List<Category>>(listOf())
    val onCategoryComplete: StateFlow<List<Category>> get() = _onCategoryComplete

    private val _onSkillsComplete = MutableStateFlow<List<Skill>>(listOf())
    val onSkillsComplete: StateFlow<List<Skill>> get() = _onSkillsComplete

    private val _onSubSkillsComplete = MutableStateFlow<List<SubSkill>>(listOf())
    val onSubSkillsComplete: StateFlow<List<SubSkill>> get() = _onSubSkillsComplete

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    fun getCategory() {
        getCategoriesUseCase.execute(
            onComplete = { categories ->
                _onCategoryComplete.update {
                    categories
                }
            },
            onFailure = { e ->
                _onFailure.update {
                    e
                }
            }
        )
    }

    fun getSkills(categoryId: String) {
        getSkillsUseCase.execute(
            categoryId = categoryId,
            onComplete = { skills ->
                _onSkillsComplete.update {
                    skills
                }
            },
            onFailure = { e ->
                _onFailure.update {
                    e
                }
            }
        )
    }

    fun getSubSkills(skillId: String) {
        getSubSkillsUseCase.execute(
            skillId = skillId,
            onComplete = { subSkills ->
                _onSubSkillsComplete.update {
                    subSkills
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