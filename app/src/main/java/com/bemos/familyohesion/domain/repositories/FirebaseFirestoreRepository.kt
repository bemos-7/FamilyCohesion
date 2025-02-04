package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.models.UserAuth

interface FirebaseFirestoreRepository {
    fun getSkills(onComplete: (Map<String, List<Skill>>) -> Unit, onFailure: (Exception) -> Unit)
    fun getUserData(onComplete: (User) -> Unit, onFailure: (Exception) -> Unit)
    fun getFamilyMembers(familyId: String, onResult: (List<FamilyMember>) -> Unit)
}