package com.bemos.familyohesion.data.remote.firebase.repository.impl

import android.util.Log
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class FirebaseFirestoreImpl(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
): FirebaseFirestoreRepository {

    override fun getUserData(onComplete: (User) -> Unit, onFailure: (Exception) -> Unit) {
        val docRef = firestore.collection("users").document(auth.currentUser!!.uid)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    onComplete(user!!)
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    override fun getFamilyMembers(familyId: String, onResult: (List<FamilyMember>) -> Unit) {
        firestore.collection("families").document(familyId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val membersList = document.get("members") as? List<Map<String, Any>>
                    val familyMembers = membersList?.mapNotNull { member ->
                        val name = member["name"] as? String
                        val relation = member["relation"] as? String
                        val points = (member["points"] as? Number)?.toDouble() ?: 0.0
                        val userId = member["userId"] as? String ?: ""
                        if (name != null && relation != null) {
                            FamilyMember(name, relation, points, userId)
                        } else {
                            null
                        }
                    } ?: emptyList()
                    onResult(familyMembers)
                } else {
                    onResult(emptyList())
                }
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
                onResult(emptyList())
            }
    }

    override fun getCategories(
        onComplete: (List<Category>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("categories").get()
            .addOnSuccessListener { documents ->
                val categories = documents.map {
                    it.toObject(Category::class.java)
                }
                onComplete(categories)
            }
            .addOnFailureListener(onFailure)
    }

    override fun getSkills(
        categoryId: String,
        onComplete: (List<Skill>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("skills").get()
            .addOnSuccessListener { documents ->
                val skills = documents.map {
                    it.toObject(Skill::class.java)
                }.filter { skill ->
                    skill.categoryId == categoryId
                }
                onComplete(skills)
            }
            .addOnFailureListener(onFailure)
    }

    override fun getSubSkills(
        skillId: String,
        onComplete: (List<SubSkill>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        firestore.collection("subSkills").get()
            .addOnSuccessListener { documents ->
                val subSkills = documents.map {
                    it.toObject(SubSkill::class.java)
                }.filter { subSkill ->
                    subSkill.skillId == skillId
                }
                onComplete(subSkills)
            }
            .addOnFailureListener(onFailure)
    }

    override fun deleteUser(
        userId: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Удаление пользователя из families
        firestore.collection("families")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (familyDocument in querySnapshot.documents) {
                    val members = familyDocument.get("members") as? List<Map<String, Any>> ?: emptyList()

                    // Удаление пользователя из массива
                    val updatedMembers = members.filterNot { it["userId"] == userId }

                    // Обновляем массив без удалённого пользователя
                    familyDocument.reference.update("members", updatedMembers)
                        .addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                }

                // Удаление пользователя из users
                firestore.collection("users").document(userId).delete()
                    .addOnSuccessListener {
                        // Удаление аккаунта из Firebase Auth
                        val user = auth.currentUser
                        if (user?.uid == userId) {
                            user.delete().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    onComplete()
                                } else {
                                    onFailure(task.exception ?: Exception("Failed to delete user account"))
                                }
                            }
                        } else {
                            onComplete()
                        }
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun updateUserDetails(
        userId: String,
        newEmail: String,
        newName: String,
        onComplete: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        // Обновление имени и почты в Firestore
        val userRef = firestore.collection("users").document(userId)

        firestore.runTransaction { transaction ->
            val userDoc = transaction.get(userRef)
            if (userDoc.exists()) {
                // Обновляем имя и почту
                transaction.update(userRef, "name", newName, "email", newEmail)
            } else {
                throw Exception("User not found")
            }
        }.addOnSuccessListener {
            // Обновляем почту в Firebase Authentication
            val user = auth.currentUser
            if (user?.email != newEmail) {
                if (user != null) {
                    user.updateEmail(newEmail).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Почта успешно обновлена
                            onComplete()
                        } else {
                            onFailure(task.exception ?: Exception("Failed to update email in Firebase Authentication"))
                        }
                    }
                }
            } else {
                onComplete()
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }

    override fun updateUserPoints(
        familyId: String,
        userId: String,
        pointsToAdd: Int
    ) {
        val db = FirebaseFirestore.getInstance()
        val familyRef = db.collection("families").document(familyId)

        familyRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val members = document.get("members") as? List<Map<String, Any>> ?: return@addOnSuccessListener

                val updatedMembers = members.map { member ->
                    if (member["userId"] == userId) {
                        val currentPoints = (member["points"] as? Long ?: 0L).toInt()
                        member.toMutableMap().apply {
                            this["points"] = currentPoints + pointsToAdd
                        }
                    } else {
                        member
                    }
                }

                familyRef.update("members", updatedMembers)
                    .addOnSuccessListener {
                        println("Очки пользователя обновлены.")
                    }
                    .addOnFailureListener { e ->
                        println("Ошибка: ${e.message}")
                    }
            }
        }.addOnFailureListener { e ->
            println("Ошибка загрузки документа: ${e.message}")
        }
    }
}