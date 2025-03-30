package com.test.core

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

val fontInter = FontFamily(
    Font(R.font.inter_black),
    Font(R.font.inter_medium, weight = FontWeight.Medium),
    Font(R.font.inter_bold, weight = FontWeight.Bold),
    Font(R.font.inter_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.inter_light, weight = FontWeight.Light),
    Font(R.font.inter_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.inter_thin, weight = FontWeight.Thin),
)

val fontSwitzer = FontFamily(
    Font(R.font.switzer_black),
    Font(R.font.switzer_medium, weight = FontWeight.Medium),
    Font(R.font.switzer_bold, weight = FontWeight.Bold),
    Font(R.font.switzer_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.switzer_light, weight = FontWeight.Light),
    Font(R.font.switzer_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.switzer_thin, weight = FontWeight.Thin),
    Font(R.font.switzer_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.switzer_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.switzer_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
)
