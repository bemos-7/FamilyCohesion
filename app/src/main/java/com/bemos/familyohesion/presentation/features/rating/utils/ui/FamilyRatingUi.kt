package com.bemos.familyohesion.presentation.features.rating.utils.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.core.ui.theme.Red
import com.bemos.familyohesion.core.ui.theme.RedAlpha03
import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.presentation.features.rating.utils.model.FamilyRating

@Composable
fun FamilyMemberRatingUi(
    modifier: Modifier = Modifier,
    family: FamilyRating,
    currentFamilyId: String,
    number: Int
) {
    if (family.family.familyId != currentFamilyId) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 19.dp)
                .height(43.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = number.toString(),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = family.family.name,
                fontSize = 16.sp
            )
            Row(
                Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = family.family.familyPoints.toString() + " XP",
                    fontSize = 16.sp
                )
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(RedAlpha03)
                .padding(horizontal = 19.dp)
                .height(43.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = number.toString(),
                fontSize = 16.sp,
                color = Red
            )
            Spacer(modifier = Modifier.width(11.dp))
            Text(
                text = family.family.name,
                fontSize = 16.sp,
                color = Red
            )
            Row(
                Modifier
                    .weight(1f),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = family.family.familyPoints.toString() + " XP",
                    fontSize = 16.sp,
                    color = Red
                )
            }
        }
    }
}

@Preview
@Composable
private fun FamilyMemberRatingUiPreview() {
    FamilyMemberRatingUi(
        family = FamilyRating(
            family = Family(
                "",
                "Some",
                "",
                listOf()
            )
        ),
        currentFamilyId = "",
        number = 1
    )
}