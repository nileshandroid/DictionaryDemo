package com.pronesh.dictionarydemo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pronesh.dictionarydemo.R

val newFont= FontFamily(Font(R.font.font_bold, FontWeight.Normal))
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = newFont,
        fontSize = 16.sp
    )
)