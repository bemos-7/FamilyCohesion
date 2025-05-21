package com.bemos.familyohesion.presentation.features.tasks.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.domain.use_cases.DeleteTaskUseCase
import com.bemos.familyohesion.domain.use_cases.GetPendingTasksUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserPointsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val getPendingTasksUseCase: GetPendingTasksUseCase,
    private val updateUserPointsUseCase: UpdateUserPointsUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _pendTasks = MutableStateFlow<List<Task>>(listOf())
    val pendTasks = _pendTasks.asStateFlow()

    init {
        getPendingTasks()
    }

    fun getPendingTasks() = viewModelScope.launch {
        val tasks = getPendingTasksUseCase.execute()
        _pendTasks.update {
            tasks
        }
    }

    fun updateUserPoints(
        task: Task
    ) {
        updateUserPointsUseCase.execute(
            familyId = task.familyId,
            userId = task.userId,
            pointsToAdd = task.pointsToAdd
        )
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun deleteTaskUseCase(
        task: Task
    ) = viewModelScope.launch {
        deleteTaskUseCase.execute(
            task = task
        )
    }

}