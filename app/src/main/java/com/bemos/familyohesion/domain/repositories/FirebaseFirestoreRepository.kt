package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.UserAuth

interface FirebaseFirestoreRepository {
    fun getSkills(onComplete: (Map<String, List<Skill>>) -> Unit, onFailure: (Exception) -> Unit)
    fun getUserData(onComplete: (UserAuth) -> Unit, onFailure: (Exception) -> Unit)
}