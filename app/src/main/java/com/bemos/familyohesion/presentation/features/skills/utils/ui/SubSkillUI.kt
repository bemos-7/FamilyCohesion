package com.bemos.familyohesion.presentation.features.skills.utils.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.app.App
import com.bemos.familyohesion.presentation.app.App.Companion.listOfSubSkills
import com.bemos.familyohesion.ui.theme.RedAlpha03

@Composable
fun SubSkillUi(
    subSkill: SubSkill,
    onEventClick: (SubSkill) -> Unit
) {
    val context = LocalContext.current
    val listOfSubSkills by remember { mutableStateOf(App.listOfSubSkills) }

//    Card(
//        modifier = Modifier
//            .height(50.dp)
//            .fillMaxWidth(),
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        ),
//        border = BorderStroke(
//            width = 1.dp,
//            color = RedAlpha03
//        ),
//        shape = RoundedCornerShape(
//            14.dp
//        )
//    ) {
//        Column(
//            modifier = Modifier.fillMaxHeight(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//            ) {
//                Text(
//                    modifier = Modifier
//                        .weight(1f)
//                        .clickable {
//                            Toast.makeText(context, subSkill.name, Toast.LENGTH_SHORT).show()
//                        },
//                    text = subSkill.name,
//                    softWrap = false,
//                )
//                Row(
//                    modifier = Modifier.weight(1f),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Icon(
//                        painter = painterResource(
//                            id = R.drawable.points_fill
//                        ),
//                        contentDescription = null,
//                        tint = Color.Unspecified
//                    )
//                    Spacer(Modifier.width(2.dp))
//                    Text(
//                        text = subSkill.points.toString()
//                    )
//                }
//                Text(
//                    text = if (listOfSubSkills.contains(subSkill)) "Завершить" else "Начать",
//                    modifier = Modifier.clickable {
//                        onEventClick(subSkill)
//                    }
//                )
//            }
//        }
//    }

    if (App.endingSubSkills.any { (endingSubSkill, _)->
        endingSubSkill == subSkill
    }) {
        SubSkillUiState(
            subSkill,
            context = context,
            alpha = 0.3f,
            onEventClick = {

            }
        )
    } else {
        SubSkillUiState(
            subSkill,
            context = context,
            onEventClick = {
                onEventClick(it)
            }
        )
    }
}

@Composable
fun SubSkillUiState(
    subSkill: SubSkill,
    context: Context,
    alpha: Float = 100f,
    onEventClick: (SubSkill) -> Unit
) {
    val checkAlpha = alpha == 0.3f
    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .alpha(alpha),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = RedAlpha03
        ),
        shape = RoundedCornerShape(
            14.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            Toast.makeText(context, subSkill.name, Toast.LENGTH_SHORT).show()
                        },
                    text = subSkill.name,
                    softWrap = false,
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.points_fill
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text = subSkill.points.toString()
                    )
                }
                Text(
                    text = if (listOfSubSkills.contains(subSkill)) "Завершить" else if (checkAlpha) "Выполнено" else "Начать",
                    modifier = Modifier.clickable {
                        onEventClick(subSkill)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SubSkillUiStatePreview() {
    SubSkillUiState(
        subSkill = SubSkill(
            id = "",
            name = "Приготовить пеперони",
            points = 2,
            skillId = ""
        ),
        context = LocalContext.current,
        alpha = 0.3f,
        onEventClick = {

        }
    )
}
@Preview
@Composable
private fun SubSkillUiPreview() {
    SubSkillUi(
        subSkill = SubSkill(
            id = "",
            name = "Приготовить пеперони",
            points = 2,
            skillId = ""
        ),
        onEventClick = {

        }
    )
}