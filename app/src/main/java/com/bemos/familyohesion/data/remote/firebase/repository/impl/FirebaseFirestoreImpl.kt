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
                        if (name != null && relation != null) {
                            FamilyMember(name, relation, points)
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
}