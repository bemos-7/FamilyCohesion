package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class GetCurrentUserPointsUseCase(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository
) {
    fun execute(
        familyId: String,
        callback: (Int?) -> Unit
    ) {
        val db = FirebaseFirestore.getInstance()
        val familyRef = db.collection("families").document(familyId)
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
            ?: run {
                callback(null)
                return
            }

        familyRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val members = document.get("members") as? List<Map<String, Any>> ?: run {
                    callback(null)
                    return@addOnSuccessListener
                }

                val user = members.find { it["userId"] == currentUserId }
                val points = (user?.get("points") as? Long)?.toInt() ?: 0
                callback(points)
            } else {
                callback(null)
            }
        }.addOnFailureListener { e ->
            println("Ошибка загрузки документа: ${e.message}")
            callback(null)
        }
    }
}