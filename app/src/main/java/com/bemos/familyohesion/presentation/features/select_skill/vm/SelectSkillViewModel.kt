package com.bemos.familyohesion.presentation.features.select_skill.vm

import androidx.lifecycle.ViewModel
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SelectSkillViewModel @Inject constructor(
    private val getSkillsUseCase: GetSkillsUseCase
) : ViewModel() {
    private val _onComplete = MutableStateFlow<(Map<String, List<Skill>>)>(emptyMap())
    val onComplete: StateFlow<(Map<String, List<Skill>>)> get() = _onComplete

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    fun getSkills() {
        getSkillsUseCase.execute(
            onComplete = { skills ->
                _onComplete.update {
                    skills
                }
            },
            onFailure = { exception ->
                _onFailure.update {
                    exception
                }
            }
        )
    }
}