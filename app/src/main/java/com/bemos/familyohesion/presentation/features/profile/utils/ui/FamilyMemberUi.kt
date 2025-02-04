package com.bemos.familyohesion.presentation.features.profile.utils.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.ui.theme.Red
import com.bemos.familyohesion.ui.theme.RedAlpha03
import kotlin.math.roundToInt

@Composable
fun FamilyMemberUi(
    familyMember: FamilyMember,
    currentUserName: String
) {
    if (familyMember.name != currentUserName) {
        Row {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 19.dp)
                    .height(43.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = familyMember.name,
                    fontSize = 16.sp
                )
                Row(
                    Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Удалить",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FamilyMemberPreview() {
    FamilyMemberUi(
        familyMember = FamilyMember(
            "Some",
            "",
            12.2
        ),
        currentUserName = "s"
    )
}