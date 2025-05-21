package com.bemos.familyohesion.presentation.features.tasks.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.DeleteTaskUseCase
import com.bemos.familyohesion.domain.use_cases.GetPendingTasksUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserPointsUseCase
import com.bemos.familyohesion.presentation.features.tasks.vm.TasksViewModel

class TasksViewModelFactory(
    private val getPendingTasksUseCase: GetPendingTasksUseCase,
    private val updateUserPointsUseCase: UpdateUserPointsUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(
            getPendingTasksUseCase = getPendingTasksUseCase,
            updateUserPointsUseCase = updateUserPointsUseCase,
            deleteTaskUseCase = deleteTaskUseCase
        ) as T
    }
}