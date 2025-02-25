package com.bemos.familyohesion.presentation.features.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.app.App
import com.bemos.familyohesion.presentation.features.skills.utils.ui.SkillUi
import com.bemos.familyohesion.presentation.features.skills.utils.ui.SubSkillUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsContent(
    categories: List<Category>,
    skills: List<Skill>,
    subSkills: List<SubSkill>?,
    onSkillClick: (String) -> Unit,
    onFinishSubSkill: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    var bottomSheetSkill by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxSize()
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
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = skills) {
                SkillUi(
                    skill = it,
                    onClick = { id ->
                        onSkillClick(id)
                        isBottomSheetVisible = true
                        bottomSheetSkill = it.name
                    }
                )
            }
        }
    }
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            sheetState = sheetState,
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                text = bottomSheetSkill,
                fontSize = 24.sp
            )
            if (subSkills != null) {
                LazyColumn(
                    modifier = Modifier.padding(
                        horizontal = 17.dp
                    ),
                ) {
                    items(items = subSkills) {
                        SubSkillUi(
                            subSkill = it,
                            onEventClick = { subSkill ->
                                if (!App.listOfSubSkills.contains(subSkill)) {
                                    App.listOfSubSkills.add(subSkill)
                                } else {
                                    onFinishSubSkill(it.name)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SkillsContentPreview() {
    SkillsContent(
        categories = listOf(
            Category(
                id = "",
                name = "Кулинария"
            )
        ),
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
        ),
        subSkills = listOf(),
        onSkillClick = {},
        onFinishSubSkill = {}
    )
}