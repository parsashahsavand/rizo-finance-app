package com.example.Parsa.presentation_Screens.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rawToJson(@RawRes resId: Int): String {
    val context = LocalContext.current
    return remember(resId) {
        context.resources.openRawResource(resId).bufferedReader().use { it.readText() }
    }
}
