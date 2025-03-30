package com.test.core

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF000000),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF9E9E9E),
    onSecondary = Color(0xFF000000),
    tertiary = Color(0xFF424242),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFF5F5F5),
    error = Color(0xFFD32F2F),
    onError = Color(0xFFFFFFFF)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFFFFF),
    onPrimary = Color(0xFF000000),
    secondary = Color(0xFFBDBDBD),
    onSecondary = Color(0xFF000000),
    tertiary = Color(0xFF616161),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFF000000),
    surface = Color(0xFF121212),
    error = Color(0xFFEF5350),
    onError = Color(0xFFFFFFFF)
)

@Composable
fun SnapBattleAppTheme(
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
        colorScheme = colorScheme,
        typography = SnapBattleTypography(1f),
        content = content
    )
}

private fun SnapBattleTypography(coefficient: Float): Typography {
    return Typography(
        displayLarge = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.ExtraBold,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(57f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(75.24f * coefficient, TextUnitType.Sp),
        ),
        displaySmall = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(32f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(36f * coefficient, TextUnitType.Sp),
        ),
        headlineMedium = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(20f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(26.4f * coefficient, TextUnitType.Sp),
        ),
        headlineSmall = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight(800),
            fontStyle = FontStyle.Italic,
            fontSize = 16.sp * coefficient,
            lineHeight = TextUnit(26.4f * coefficient, TextUnitType.Sp),
        ),
        labelLarge = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(16f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(21.12f * coefficient, TextUnitType.Sp),
        ),
        labelMedium = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSize = TextUnit(14f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(18.48f * coefficient, TextUnitType.Sp),
        ),
        titleLarge = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(24f * coefficient, TextUnitType.Sp),
        ),
        titleSmall = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight.Bold,
            fontSize = TextUnit(16f * coefficient, TextUnitType.Sp),
        ),
        titleMedium = TextStyle(
            fontFamily = fontSwitzer,
            fontWeight = FontWeight(800),
            fontSize = 20.sp * coefficient,
            lineHeight = 24.sp * coefficient,
        ),
        bodySmall = TextStyle(
            fontFamily = fontInter,
            fontWeight = FontWeight.Medium,
            fontSize = TextUnit(12f * coefficient, TextUnitType.Sp),
            lineHeight = TextUnit(16f * coefficient, TextUnitType.Sp),
        ),
        bodyMedium = TextStyle(
            fontFamily = fontInter,
            fontWeight = FontWeight(500),
            fontSize = 14.sp * coefficient,
            lineHeight = 20.sp * coefficient,
        ),
    )
}
