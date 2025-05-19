package com.bemos.familyohesion.presentation.features.tasks

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bemos.familyohesion.presentation.features.tasks.vm.TasksViewModel
import com.bemos.familyohesion.presentation.features.tasks.vm.factory.TasksViewModelFactory

@Composable
fun TasksScreen(
    tasksViewModelFactory: TasksViewModelFactory
) {

    val viewModel = viewModel<TasksViewModel>(
        factory = tasksViewModelFactory
    )

    val pendTasks = viewModel.pendTasks.collectAsState().value

    Log.d("tasks", pendTasks.toString())

    TasksContent(
        tasks = pendTasks
    )
}