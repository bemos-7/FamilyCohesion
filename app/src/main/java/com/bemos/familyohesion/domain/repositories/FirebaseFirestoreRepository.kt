package com.bemos.familyohesion.domain.repositories

import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.models.UserAuth

interface FirebaseFirestoreRepository {
    fun getUserData(onComplete: (User) -> Unit, onFailure: (Exception) -> Unit)
    fun getFamilyMembers(familyId: String, onResult: (List<FamilyMember>) -> Unit)
    fun getCategories(onComplete: (List<Category>) -> Unit, onFailure: (Exception) -> Unit)
    fun getSkills(categoryId: String, onComplete: (List<Skill>) -> Unit, onFailure: (Exception) -> Unit)
    fun getSubSkills(skillId: String, onComplete: (List<SubSkill>) -> Unit, onFailure: (Exception) -> Unit)
    fun deleteUser(userId: String, onComplete: () -> Unit, onFailure: (Exception) -> Unit)
    fun updateUserDetails(userId: String, newEmail: String, newName: String,onComplete: () -> Unit, onFailure: (Exception) -> Unit)
    fun updateUserPoints(familyId: String, userId: String, pointsToAdd: Int)
}