package com.bemos.familyohesion.presentation.features.skills.utils.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.R
import com.bemos.familyohesion.core.ui.theme.Red
import com.bemos.familyohesion.domain.models.Category
import java.util.Locale

@Composable
fun DropDownMenuCustom(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    isVisibleChange: () -> Unit,
    categories: List<Category>,
    selectedCategory: (Category) -> Unit
) {
    var showCategory by remember {
        mutableStateOf(Category())
    }

    LaunchedEffect(categories) {
        if (categories.isNotEmpty()) {
            showCategory = categories[0]
        }
    }
    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.clip(
                RoundedCornerShape(28.dp)
            ).clickable {
                isVisibleChange()
            },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Red
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 5.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (categories.isNotEmpty()) showCategory.name.uppercase(Locale.getDefault()) else "loading...",
                    color = Red,
                    fontSize = 20.sp
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Icon(
                    modifier = Modifier.size(width = 12.dp, height = 7.dp),
                    painter = painterResource(R.drawable.keyboard_arrow_down),
                    contentDescription = "arrow",
                    tint = Red
                )
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                animationSpec = spring(
                    stiffness = 300f,
                    dampingRatio = 0.3f
                )
            ) + slideInVertically(
                animationSpec = spring(
                    stiffness = 300f,
                    dampingRatio = 0.3f
                )
            ),
            exit = fadeOut(
                animationSpec = spring(
                    stiffness = 300f,
                    dampingRatio = 0.5f
                )
            ) + slideOutVertically(
                animationSpec = spring(
                    stiffness = 300f,
                    dampingRatio = 0.5f
                )
            )
        ) {
            Column {
                Spacer(modifier = Modifier.height(40.dp))
                LazyColumn {
                    items(items = categories) {
                        if (it != showCategory) {
                            DropDownMenuItem(
                                category = it,
                                isShow = true,
                                onCategoryClick = { currentCategory ->
                                    selectedCategory(currentCategory)
                                    showCategory = currentCategory
                                    isVisibleChange()
                                }
                            )
                            Spacer(Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropDownMenuItem(
    category: Category,
    isShow: Boolean,
    onCategoryClick: (Category) -> Unit
) {
    if (isShow) {
        Card(
            modifier = Modifier
                .clip(RoundedCornerShape(28.dp))
                .clickable {
                    onCategoryClick(category)
                },
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Red
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 5.dp
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = category.name.uppercase(Locale.getDefault()),
                    color = Red,
                    fontSize = 20.sp
                )
            }
        }
    }


}

@Preview
@Composable
private fun DropDownMenuCustomPreview() {
    DropDownMenuCustom(
        categories = listOf(
            Category(
                id = "",
                name = "something"
            )
        ),
        isVisible = true,
        isVisibleChange = {},
        selectedCategory = {}
    )
}

@Preview
@Composable
private fun DropDownMenuItemPreview() {
    DropDownMenuItem(
        Category(
            id = "",
            name = "android"
        ),
        isShow = true,
        onCategoryClick = {

        }
    )
}