package com.bemos.familyohesion.presentation.features.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.core.ui.theme.Gray
import com.bemos.familyohesion.domain.models.Task
import com.bemos.familyohesion.presentation.features.tasks.utils.ui.TaskCardItem

@Composable
fun TasksContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Gray)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = "Задания на проверку",
                fontSize = 26.sp,
            )

            Spacer(Modifier.height(10.dp))

            LazyColumn {
                items(items = tasks) {
                    TaskCardItem(
                        it
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TasksContentPreview() {
    TasksContent(
        tasks = listOf(
            Task(
                id = "",
                imageUrl = "",
                familyId = "",
                userId = "",
                pointsToAdd = 0,
                name = "Приготовление супа",
                isApproved = false
            )
        )
    )
}