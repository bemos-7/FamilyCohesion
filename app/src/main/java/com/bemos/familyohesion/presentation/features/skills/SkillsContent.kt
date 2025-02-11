package com.bemos.familyohesion.presentation.features.skills

import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.presentation.features.skills.utils.ui.SkillUi
import java.util.Locale

@Composable
fun SkillsContent(
    categories: List<Category>,
    skills: List<Skill>
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
           Text(
               text = if (categories.isNotEmpty()) categories[0].name else "loading...",
               fontSize = 20.sp
           )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.points),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(1.dp))
                Text(
                    text = "200",
                    fontSize = 20.sp
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(items = skills) {
                SkillUi(skill = it)
            }
        }
    }
}

@Preview
@Composable
private fun SkillsContentPreview() {
    SkillsContent(
        categories = listOf(Category(
            id = "",
            name = "Кулинария"
        )),
        skills = listOf(
            Skill(
                id = "",
                name = "Приготовление супов",
                categoryId = "",
                subSkills = listOf()
            ),
            Skill(
                id = "",
                name = "Приготовление супов",
                categoryId = "",
                subSkills = listOf()
            ),
            Skill(
                id = "",
                name = "Приготовление супов",
                categoryId = "",
                subSkills = listOf()
            ),
        )
    )
}