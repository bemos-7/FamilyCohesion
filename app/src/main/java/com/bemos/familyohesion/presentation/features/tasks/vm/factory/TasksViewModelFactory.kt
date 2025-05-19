package com.bemos.familyohesion.presentation.features.tasks.vm.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bemos.familyohesion.domain.use_cases.GetPendingTasksUseCase
import com.bemos.familyohesion.presentation.features.tasks.vm.TasksViewModel

class TasksViewModelFactory(
    private val getPendingTasksUseCase: GetPendingTasksUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TasksViewModel(
            getPendingTasksUseCase = getPendingTasksUseCase
        ) as T
    }
}