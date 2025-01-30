package com.bemos.familyohesion.presentation.features.select_skill

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.shared.utils.ui.text_field.CustomTextField
import java.util.Locale

@Composable
fun SelectSkillContent(
    skills: (Map<String, List<Skill>>)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Выберите скилл",
                fontSize = 40.sp
            )
        }
        Spacer(modifier = Modifier.height(43.dp))
        LazyColumn{
            items(
                skills.keys.toList()
            ) {
                Text(
                    text = it,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
        Spacer(modifier = Modifier.height(52.dp))
        CustomButton(
            onClick = {

            },
            content = "Продолжить"
        )
    }
}

@Preview
@Composable
private fun SelectSkillContentPreview() {
    SelectSkillContent(mapOf())
}