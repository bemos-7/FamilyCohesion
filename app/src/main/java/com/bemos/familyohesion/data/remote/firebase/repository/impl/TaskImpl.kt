package com.bemos.familyohesion.data.remote.firebase.repository.impl

import android.net.Uri
import android.util.Log
import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.domain.repositories.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID

class TaskImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseStorage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : TaskRepository {
    override suspend fun uploadImageAndGetUrl(
        imageUri: Uri
    ): String {
        val userId = firebaseAuth.currentUser?.uid
        val fileName = "images/$userId.jpg"
        val imageRef: StorageReference = firebaseStorage.reference.child(fileName)

        val uploadTask = imageRef.putFile(imageUri).await()

        val downloadUrl = imageRef.downloadUrl.await().toString()
        return downloadUrl
    }

    override suspend fun createTask(
        imageUri: Uri,
        familyId: String,
        userId: String,
        pointsToAdd: Int,
        name: String
    ) {
        try {
            val imageUrl = uploadImageAndGetUrl(imageUri)
            val taskId = UUID.randomUUID().toString()

            val task = Task(
                id = taskId,
                imageUrl = imageUrl,
                familyId = familyId,
                userId = userId,
                pointsToAdd = pointsToAdd,
                name = name
            )

            firestore.collection("tasks")
                .document(taskId)
                .set(task)
                .await()

            Log.d("CreateTask", "Create task successfully!")
        } catch (e: Exception) {
            Log.d("CreateTask", e.message.toString())
        }
    }

    override suspend fun getPendingTasks(): List<Task> {
        return try {
            val snapshot = firestore.collection("tasks").get().await()
            snapshot.documents.mapNotNull {
                it.toObject(Task::class.java)
            }
        } catch (e: Exception) {
            Log.d("GetPendingTasks", e.message.toString())
            emptyList()
        }
    }

    override suspend fun deleteTask(
        task: Task
    ) {
        try {
            firestore.collection("tasks").document(task.id).delete().await()
            val imageName = "${task.userId}.jpg"
            val imageRef = firebaseStorage.reference.child("images/$imageName")
            imageRef.delete().await()
            Log.d("DeleteTast", "Task deleted successfully!")
        } catch(e: Exception) {
            Log.d("DeleteTast", e.message.toString())
        }
    }
}