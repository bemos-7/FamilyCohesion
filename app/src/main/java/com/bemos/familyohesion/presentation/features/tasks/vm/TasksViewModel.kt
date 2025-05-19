package com.bemos.familyohesion.presentation.features.tasks.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.domain.use_cases.GetPendingTasksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val getPendingTasksUseCase: GetPendingTasksUseCase
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

}