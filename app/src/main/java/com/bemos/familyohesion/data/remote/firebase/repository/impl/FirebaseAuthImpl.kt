package com.bemos.familyohesion.data.remote.firebase.repository.impl

import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class FirebaseAuthImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): FirebaseAuthRepository {

    override fun signUp(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener
                    val familyId = UUID.randomUUID().toString()

                    val newFamily = Family(
                        familyId = familyId,
                        adminId = userId,
                        members = listOf(
                            mapOf(
                                "name" to userAuth.name,
                                "points" to 0,
                                "relation" to userAuth.userRole
                            )
                        )
                    )

                    val familyDocRef = firebaseFirestore.collection("families").document(familyId)
                    val userDocRef = firebaseFirestore.collection("users").document(userId)

                    firebaseFirestore.runBatch { batch ->
                        batch.set(familyDocRef, newFamily)
                        batch.set(
                            userDocRef,
                            mapOf(
                                "userId" to userId,
                                "email" to userAuth.email,
                                "name" to userAuth.name,
                                "familyId" to familyId,
                                "role" to userAuth.userRole
                            )
                        )
                    }.addOnSuccessListener {
                        onSuccess()
                    }.addOnFailureListener { exception ->
                        onFailure(exception)
                    }
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

    override fun signIn(userAuth: UserAuth, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(userAuth.email, userAuth.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    task.exception?.let { onFailure(it) }
                }
            }
    }

}