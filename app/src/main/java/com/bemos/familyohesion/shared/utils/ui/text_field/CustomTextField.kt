package com.bemos.familyohesion.shared.utils.ui.text_field

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bemos.familyohesion.core.ui.theme.RedAlpha

@Composable
fun CustomTextField(
    text: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = RedAlpha,
                shape = RoundedCornerShape(16.dp),
            ),
        shape = RoundedCornerShape(16.dp),
        value = text, 
        onValueChange = {
            onValueChange(it)
        },
        label = {
            Text(
                text = label
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White
        )
    )
}

@Preview
@Composable
private fun CustomTextFieldPreview() {
    CustomTextField(
        text = "",
        label = "Name",
        onValueChange = {}
    )
}