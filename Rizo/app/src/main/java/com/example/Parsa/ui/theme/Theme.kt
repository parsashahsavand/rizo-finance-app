package com.example.Parsa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🌙 حالت تاریک (Dark Mode)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),     // فیروزه‌ای روشن برای آیکون و متن
    secondary = Color(0xFF37474F),   // خاکستری مایل به آبی (برای فوتر و هدر)
    background = Color(0xFF121212),  // مشکی نرم برای پس‌زمینه کلی
    surface = Color(0xFF263238),     // برای کارت‌ها و سطوح
    onPrimary = Color.Black,         // متن روی رنگ اصلی
    onSecondary = Color.White,       // متن روی فوتر
    surfaceDim = Color.Yellow,  // اینو من از خودم زدم ورودی رو برای بک گراند چالشه
)

// ☀️ حالت روشن (Light Mode)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00796B),     // سبز تیره مدرن
    secondary = Color(0xFFC7EFEE),   // سبز خیلی روشن برای فوتر و سطح
    background = Color(0xFFFDFDFD),  // سفید طبیعی پس‌زمینه
    surface = Color(0xFFF1F1F1),     // خاکستری خیلی روشن
    onPrimary = Color.White,         // متن روی primary
    onSecondary = Color.Black,       // متن روی secondary
    surfaceDim = Color(0xFFFF8B00), // اینو من از خودم زدم ورودی رو برای بک گراند چالشه
)

@Composable
fun CaraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
