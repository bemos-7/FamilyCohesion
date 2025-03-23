package com.bemos.familyohesion.presentation.features.finish_subSkill.navigation

import kotlinx.serialization.Serializable

@Serializable
data class FinishSubSkillScreen(
    val subSkillName: String,
    val points: Int
)