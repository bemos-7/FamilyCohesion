package com.bemos.familyohesion.domain.repositories

import android.net.Uri
import com.bemos.familyohesion.domain.models.Task

interface TaskRepository {
    suspend fun uploadImageAndGetUrl(imageUri: Uri) : String
    suspend fun createTask(imageUri: Uri, familyId: String, userId: String, pointsToAdd: Int, name: String)
    suspend fun getPendingTasks(): List<Task>
}