package com.bemos.familyohesion.presentation.features.rating

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.core.ui.theme.White
import com.bemos.familyohesion.domain.models.Family
import com.bemos.familyohesion.presentation.features.rating.utils.model.FamilyRating
import com.bemos.familyohesion.presentation.features.rating.utils.ui.FamilyMemberRatingUi

@Composable
fun RatingContent(
    families: List<FamilyRating>,
    currentFamilyId: String?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 50.dp)
        ) {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = "Рейтинг",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(26.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                LazyColumn {
                    itemsIndexed(
                        items = families
                    ) { index, family ->
                        FamilyMemberRatingUi(
                            family = family,
                            currentFamilyId = currentFamilyId ?: "",
                            number = index + 1
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RatingContentPreview() {
    RatingContent(
        families = listOf(
            FamilyRating(
                family = Family(
                    "1",
                    "Some",
                    "",
                    listOf()
                )
            ),
            FamilyRating(
                family = Family(
                    "",
                    "Some",
                    "",
                    listOf()
                )
            )
        ),
        currentFamilyId = ""
    )
}