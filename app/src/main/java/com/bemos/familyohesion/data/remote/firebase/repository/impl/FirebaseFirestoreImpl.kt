package com.bemos.familyohesion.data.remote.firebase.repository.impl

import android.util.Log
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
    override fun getSkills(onComplete: (Map<String, List<Skill>>) -> Unit, onFailure: (Exception) -> Unit) {
//        val skillsMap = mutableMapOf<String, MutableList<Skill>>()
//
//        firestore.collection("skills").get().addOnSuccessListener { categoriesSnapshot ->
//            val totalCategories = categoriesSnapshot.size()
//            var processedCategories = 0
//
//            for (categoryDoc in categoriesSnapshot.documents) {
//                val categoryName = categoryDoc.id
//                val skillsList = mutableListOf<Skill>()
//
//                categoryDoc.reference.collection("skills").get().addOnSuccessListener { skillsSnapshot ->
//                    for (skillDoc in skillsSnapshot.documents) {
//                        val skillName = skillDoc.getString("name") ?: "Unknown Skill"
//                        val subSkills = mutableListOf<SubSkill>()
//
//                        val subSkillsList = skillDoc.get("subSkills") as? List<Map<String, Any>> ?: emptyList()
//                        for (subSkillData in subSkillsList) {
//                            val subSkillName = subSkillData["name"] as? String ?: "Unknown SubSkill"
//                            val subSkillPoints = (subSkillData["points"] as? Long)?.toInt() ?: 1
//                            subSkills.add(SubSkill(subSkillName, subSkillPoints))
//                        }
//
//                        skillsList.add(Skill(skillName, subSkills))
//                    }
//
//                    skillsMap[categoryName] = skillsList.toMutableList()
//
//                    processedCategories++
//                    if (processedCategories == totalCategories) {
//                        onComplete(skillsMap)
//                        Log.d("aalskdjf", skillsMap.toString())
//                    }
//                }
//            }
//        }.addOnFailureListener { exception ->
//            onFailure(exception)
//        }
    }

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
}