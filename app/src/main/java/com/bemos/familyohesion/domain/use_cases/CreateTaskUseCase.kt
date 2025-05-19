package com.bemos.familyohesion.domain.use_cases

import android.net.Uri
import com.bemos.familyohesion.domain.repositories.TaskRepository

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(
        imageUri: Uri,
        familyId: String,
        userId: String,
        pointsToAdd: Int,
        name: String
    ) {
        taskRepository.createTask(
            imageUri = imageUri,
            familyId = familyId,
            userId = userId,
            pointsToAdd = pointsToAdd,
            name = name
        )
    }
}