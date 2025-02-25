package com.bemos.familyohesion.presentation.features.finish_subSkill

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.presentation.features.finish_subSkill.util.ui.TimeUi
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.ui.theme.LightGray
import com.bemos.familyohesion.ui.theme.Red
import com.bemos.familyohesion.ui.theme.RedAlpha03

@Composable
fun FinishSubSkillContent(
    subSkillName: String,
    familyMembers: List<FamilyMember>
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var dropMenuContent by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(Modifier.height(22.dp))
        Row(
            Modifier.padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_2),
                contentDescription = "back"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Назад",
                fontSize = 18.sp
            )
        }
        Spacer(Modifier.height(22.dp))

        Spacer(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(RedAlpha03)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.width(340.dp),
                text = subSkillName,
                fontSize = 32.sp,
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Укажите напарника. Кто из семьи вам помогал?",
                fontSize = 15.sp
            )
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CardDefaults.shape)
                        .clickable {
                            expanded = true
                        },
                    border = BorderStroke(
                        width = 1.dp, color = LightGray
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 12.dp,
                                bottom = 12.dp,
                                end = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = dropMenuContent,
                            fontSize = 16.sp
                        )
                        Icon(
                            painter = painterResource(R.drawable.round_keyboard_arrow_down_24),
                            contentDescription = "arrow"
                        )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    familyMembers.forEach { member ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    dropMenuContent = "${member.name} (${member.relation})"
                                }
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            text = "${member.name} (${member.relation})"
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Добавить в семью пользователя",
                fontSize = 16.sp,
                color = Red
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Сколько времени вы провели с семьей?",
                fontSize = 18.sp
            )
            Spacer(Modifier.height(13.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TimeUi(
                    time = 30
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 60
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 90
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 120
                )
            }
            Spacer(Modifier.height(24.dp))
            CustomButton(
                onClick = {

                },
                content = "Завершить"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FinishSubSkillContentPreview() {
    FinishSubSkillContent(
        "Приготовить пеперони",
        familyMembers = listOf(
            FamilyMember(
                name = "Some",
                relation = "Something",
                points = 0.0
            )
        )
    )
}