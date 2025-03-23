package com.bemos.familyohesion.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val LightFamilyScheme = lightColorScheme(
    primary = Color(0xFFD32F2F), // Основной цвет, например, для заголовков и акцентов
    secondary = Color(0xFF388E3C), // Вторичный цвет, например, для кнопок и интерактивных элементов
    background = Color(0xFFFFFFFF), // Фоновый цвет
    surface = Color(0xFFFFFFFF), // Цвет поверхностей, например, карточек
    error = Color(0xFFD32F2F), // Цвет для ошибок
    onPrimary = Color(0xFFFFFFFF), // Цвет текста на основном цвете
    onSecondary = Color(0xFFFFFFFF), // Цвет текста на вторичном цвете
    onBackground = Color(0xFF000000), // Цвет текста на фоновом цвете
    onSurface = Color(0xFF000000), // Цвет текста на поверхностях
    onError = Color(0xFFFFFFFF) // Цвет текста на ошибках
)

@Composable
fun FamilyСohesionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = LightFamilyScheme,
        typography = Typography,
        content = content
    )
}