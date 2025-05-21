package com.bemos.familyohesion.domain.use_cases

import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.domain.repositories.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    suspend fun execute(
        task: Task
    ) {
        taskRepository.deleteTask(task)
    }
}