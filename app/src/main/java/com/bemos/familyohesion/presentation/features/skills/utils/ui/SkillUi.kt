package com.bemos.familyohesion.presentation.features.skills.utils.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.ui.theme.RedAlpha03

@Composable
fun SkillUi(
    skill: Skill,
    onClick: (String) -> Unit
) {
    Card(
        border = BorderStroke(width = 1.dp, RedAlpha03),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .height(80.dp)
            .clickable {
            onClick(skill.id)
        }
    ) {
        Row(
           modifier = Modifier
               .fillMaxHeight()
               .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
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
        ),
        onClick = {}
    )
}