package com.bemos.familyohesion.presentation.main_activity.util

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.bemos.familyohesion.R

data class BottomNavItem(
    val icon: Int,
    val title: String,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem(
        icon = R.drawable.outline_school_24,
        title = "Обучение",
        route = "skills"
    ),
    BottomNavItem(
        icon = R.drawable.hotel_class,
        title = "Рейтинг",
        route = "rating"
    ),
    BottomNavItem(
        icon = R.drawable.profile,
        title = "Профиль",
        route = "profile"
    ),
)