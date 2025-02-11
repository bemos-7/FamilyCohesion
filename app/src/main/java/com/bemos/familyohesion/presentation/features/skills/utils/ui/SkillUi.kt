package com.bemos.familyohesion.presentation.features.skills.utils.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.ui.theme.RedAlpha03

@Composable
fun SkillUi(
    skill: Skill
) {
    Card(
        border = BorderStroke(width = 1.dp, RedAlpha03),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 14.dp)
        ) {
            Text(
                text = skill.name,
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
private fun SkillUiPreview() {
    SkillUi(
        skill = Skill(
            id = "",
            name = "Приготовление супов",
            categoryId = "",
            subSkills = listOf()
        )
    )
}