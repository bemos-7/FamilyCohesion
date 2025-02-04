package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository

class GetFamilyMembersUseCase(
    private val repository: FirebaseFirestoreRepository
) {
    fun execute(familyId: String, onResult: (List<FamilyMember>) -> Unit) {
        repository.getFamilyMembers(
            familyId = familyId,
            onResult = onResult
        )
    }
}