package com.bemos.familyohesion.presentation.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.ui.theme.White

@Composable
fun ProfileContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Name",
                    fontSize = 32.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        text = "почта: "
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = "пароль: "
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Рейтинг",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 19.dp)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Семья",
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(10.dp))

            }
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview(modifier: Modifier = Modifier) {
    ProfileContent()
}