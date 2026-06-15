package com.example.Parsa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.Parsa.presentation_Screens.components.Header.HeaderViewModel

@Composable
fun insertGradient(
    item: String,
    viewModel: HeaderViewModel = hiltViewModel()
): Brush {
    val isDark by viewModel.darkMode.collectAsState()

    val light = mapOf(
        "income" to listOf(Color(0xFF2FDC10), Color(0xCE256A0E)),
        "cost" to listOf(Color(0xFFDC1010), Color(0xCE6A0E0E)),
        "savings" to listOf(Color(0xFFDCD910), Color(0xCE6A680E)),
        "note" to listOf(Color(0xFFDC7910), Color(0xCE6A410E)),
        "request" to listOf(Color(0xFF10DCDC), Color(0xCE0E626A)),
        "debt" to listOf(Color(0xFF2F10DC), Color(0xCE11137D)),
    )

    val dark = mapOf(
        "income" to listOf(Color(0xFF1A5D11), Color(0xFF14320B)),
        "cost" to listOf(Color(0xFF8A0B0B), Color(0xCE3A0808)),
        "savings" to listOf(Color(0xFF8A8710), Color(0xCE3A380A)),
        "note" to listOf(Color(0xFF8A4A0C), Color(0xCE3A2308)),
        "request" to listOf(Color(0xFF0A8A8A), Color(0xCE083A40)),
        "debt" to listOf(Color(0xFF1C0A8A), Color(0xCE0A0A47)),
    )

    val selected = if (isDark) dark[item] else light[item]

    return Brush.linearGradient(selected ?: listOf(Color.Gray, Color.DarkGray))
}

