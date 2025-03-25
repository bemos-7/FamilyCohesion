package com.bemos.familyohesion.presentation.features.finish_subSkill.navigation

import com.bemos.familyohesion.domain.models.SubSkill
import kotlinx.serialization.Serializable

@Serializable
data class FinishSubSkillScreen(
    val familyId: String,
    val subSkill: SubSkill
)