package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.domain.repositories.TaskRepository

class GetPendingTasksUseCase(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(): List<Task> {
        return taskRepository.getPendingTasks()
    }
}