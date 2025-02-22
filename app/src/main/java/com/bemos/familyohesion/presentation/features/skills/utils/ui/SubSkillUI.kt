package com.bemos.familyohesion.presentation.features.skills.utils.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.ui.theme.RedAlpha03

@Composable
fun SubSkillUi(
    subSkill: SubSkill
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
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
                    modifier = Modifier.weight(1f),
                    text = subSkill.name,
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
                    text = "Начать"
                )
            }
        }
    }
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
        )
    )
}