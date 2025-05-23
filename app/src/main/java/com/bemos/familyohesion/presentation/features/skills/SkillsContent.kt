package com.bemos.familyohesion.presentation.features.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
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
import com.bemos.familyohesion.core.ui.theme.White
import com.bemos.familyohesion.domain.models.Category
import com.bemos.familyohesion.domain.models.Skill
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.app.App
import com.bemos.familyohesion.presentation.features.skills.utils.ui.DropDownMenuCustom
import com.bemos.familyohesion.presentation.features.skills.utils.ui.DropDownMenuItem
import com.bemos.familyohesion.presentation.features.skills.utils.ui.SkillUi
import com.bemos.familyohesion.presentation.features.skills.utils.ui.SubSkillUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsContent(
    points: Int,
    categories: List<Category>,
    skills: List<Skill>,
    subSkills: List<SubSkill>?,
    localSubSkills: List<SubSkill>,
    onSkillClick: (String) -> Unit,
    onFinishSubSkill: (SubSkill) -> Unit,
    selectedCategory: (Category) -> Unit,
    onSubSkillClick: (SubSkill) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetVisible by remember {
        mutableStateOf(false)
    }
    var bottomSheetSkill by remember {
        mutableStateOf("")
    }
    var isVisible by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .background(White)
            .padding(top = 50.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "XP",
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = points.toString(),
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
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
        DropDownMenuCustom(
            isVisible = isVisible,
            isVisibleChange = {
                isVisible = !isVisible
            },
            categories = categories,
            selectedCategory = {
                selectedCategory(it)
            },
            modifier = Modifier
                .align(
                    Alignment.TopStart
                )
                .padding(start = 16.dp)
        )
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                isBottomSheetVisible = false
            },
            sheetState = sheetState,
            containerColor = Color.White
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
                            localSubSkill = localSubSkills,
                            onEventClick = { subSkill ->
                                if (!localSubSkills.contains(subSkill)) {
                                    onSubSkillClick(subSkill)
                                } else {
                                    onFinishSubSkill(it)
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
        onFinishSubSkill = {},
        selectedCategory = {},
        onSubSkillClick = {},
        localSubSkills = listOf(),
        points = 0
    )
}