package com.bemos.familyohesion.presentation.features.tasks.utils.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.bemos.familyohesion.core.ui.theme.Gray
import com.bemos.familyohesion.core.ui.theme.Red
import com.bemos.familyohesion.domain.models.Task

@Composable
fun TaskCardItem(
    task: Task
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(width = 1.dp, Red)
    ) {
        Row(
            Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier.size(120.dp).padding(5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Gray
                ),
            ) {
                AsyncImage(
                    model = task.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(

            ) {
                Spacer(Modifier.height(15.dp))
                Text(
                    text = task.name,
                    fontSize = 18.sp
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = task.pointsToAdd.toString() + " XP",
                    fontSize = 18.sp
                )
            }
        }

    }
}

@Preview
@Composable
private fun TaskCardItemPreview() {
    TaskCardItem(
        task = Task(
        id = "877377373",
        imageUrl = "",
        familyId = "",
        userId = "",
        pointsToAdd = 21,
        name = "Приготовление супа",
        isApproved = false
        )
    )
}