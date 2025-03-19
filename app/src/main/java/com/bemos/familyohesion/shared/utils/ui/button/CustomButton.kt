package com.bemos.familyohesion.shared.utils.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bemos.familyohesion.core.ui.theme.Red

@Composable
fun CustomButton(
    onClick: () -> Unit,
    content: String
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
        ,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Red
        )
    ) {
        Text(
            text = content,
            color = Color.White
        )
    }
}