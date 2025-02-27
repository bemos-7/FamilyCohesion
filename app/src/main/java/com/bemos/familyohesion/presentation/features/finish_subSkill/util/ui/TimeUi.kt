package com.bemos.familyohesion.presentation.features.finish_subSkill.util.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.ui.theme.Red

@Composable
fun TimeUi(
    time: Int,
    isPressed: Boolean,
    onClick: () -> Unit
) {
    if (isPressed) {
        TimeUiState(
            time = time,
            backgroundColor = Red,
            contentColor = Color.White,
            onClick = {
                onClick()
            }
        )
    } else {
        TimeUiState(
            time = time,
            backgroundColor = Color.White,
            contentColor = Red,
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun TimeUiState(
    time: Int,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(84.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Red
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${time} мин",
                color = contentColor,
                fontSize = 14.sp,
            )
        }
    }
}

@Preview
@Composable
private fun TimeUiPreview() {
    TimeUi(
        time = 30,
        isPressed = false,
        onClick = {}
    )
}