package com.bemos.familyohesion.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val customFont = FontFamily(
    Font(R.font.nutinoo, FontWeight.Normal)
)

val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = customFont,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = customFont,
        fontSize = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = customFont,
        fontSize = 14.sp
    )
)