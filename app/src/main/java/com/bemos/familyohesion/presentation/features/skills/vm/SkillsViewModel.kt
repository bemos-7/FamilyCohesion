package com.bemos.familyohesion.presentation.features.skills.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.use_cases.GetAllSubSkillsRoomUseCase
import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserPointsUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.InsertSubSkillRoomUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SkillsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSkillsUseCase: GetSkillsUseCase,
    private val getSubSkillsUseCase: GetSubSkillsUseCase,
    private val getAllSubSkillsRoomUseCase: GetAllSubSkillsRoomUseCase,
    private val insertSubSkillRoomUseCase: InsertSubSkillRoomUseCase,
    private val getFamilyIdForCurrentUserUseCase: GetFamilyIdForCurrentUserUseCase,
    private val getCurrentUserPointsUseCase: GetCurrentUserPointsUseCase
): ViewModel() {
    private val _onCategoryComplete = MutableStateFlow<List<Category>>(listOf())
    val onCategoryComplete: StateFlow<List<Category>> get() = _onCategoryComplete

    private val _onSkillsComplete = MutableStateFlow<List<Skill>>(listOf())
    val onSkillsComplete: StateFlow<List<Skill>> get() = _onSkillsComplete

    private val _onSubSkillsComplete = MutableStateFlow<List<SubSkill>>(listOf())
    val onSubSkillsComplete: StateFlow<List<SubSkill>> get() = _onSubSkillsComplete

    private val _onFailure = MutableStateFlow<Exception?>(null)
    val onFailure: StateFlow<Exception?> get() = _onFailure

    private val _subSkillsRoom = MutableStateFlow<List<SubSkill>>(listOf())
    val subSkillRoom: StateFlow<List<SubSkill>> get() = _subSkillsRoom

    private val _familyIdCallback = MutableStateFlow<String?>(null)
    val familyIdCallback: StateFlow<String?> get() = _familyIdCallback

    private val _pointsCallback = MutableStateFlow<Int?>(null)
    val pointsCallback: StateFlow<Int?> get() = _pointsCallback

    init {
        getFamilyId()
    }

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

    fun getAllSubSkillsFromRoom() = viewModelScope.launch {
        getAllSubSkillsRoomUseCase.execute()
            .collect { subSkillList ->
                _subSkillsRoom.update { subSkillList }
            }
    }

    fun insertSubSkillInRoom(subSkill: SubSkill) = viewModelScope.launch {
        insertSubSkillRoomUseCase.execute(subSkill)
        getAllSubSkillsFromRoom()
    }

    private fun getFamilyId() {
        getFamilyIdForCurrentUserUseCase.execute { id ->
            if (id != null)
                getCurrentUserPoints(id)
        }

    }

    private fun getCurrentUserPoints(
        familyId: String
    ) {
        getCurrentUserPointsUseCase.execute(
            familyId,
            callback = { points ->
                _pointsCallback.update {
                    points
                }
            }
        )
    }
}