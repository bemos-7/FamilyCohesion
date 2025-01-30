package com.bemos.familyohesion.domain.models

data class Skill(
    val name: String,
    val subSkills: List<SubSkill>
)