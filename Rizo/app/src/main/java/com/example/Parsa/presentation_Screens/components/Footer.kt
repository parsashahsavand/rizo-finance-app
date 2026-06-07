package com.example.Parsa.presentation_Screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.Parsa.presentation_Screens.aa_Navigation.Insert
import com.example.Parsa.presentation_Screens.aa_Navigation.List
import com.example.Parsa.presentation_Screens.aa_Navigation.Mian
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Footer(
    navController: NavController,
    currentScreen: Int,
    onScreenChange: (Int) -> Unit
) {
    val footerRoutes = listOf(Mian, Insert, List)
    val colors = MaterialTheme.colorScheme
    val unselectedColor = colors.onSecondary.copy(alpha = 0.6f)

    // کنترل لودینگ و ناوبری
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column {
        // لودینگ بار بالای فوتر
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = colors.primary,
                trackColor = colors.onSecondary
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
                .background(colors.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            footerRoutes.forEachIndexed { index, route ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            // جلوگیری از کلیک تکراری در حین لودینگ
                            if (currentScreen != index && !isLoading) {
                                isLoading = true
                                onScreenChange(index)

                                scope.launch {
                                    navController.navigate(route) {
                                        launchSingleTop = true
                                        restoreState = true
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                    }
                                    // لودینگ رو بعد از 400 میلی ثانیه مخفی کن
                                    delay(1000)
                                    isLoading = false
                                }
                            }
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val iconVector = when (index) {
                        0 -> Icons.Default.Home
                        1 -> Icons.Default.Add
                        2 -> Icons.Default.List
                        else -> Icons.Default.Home
                    }

                    Icon(
                        imageVector = iconVector,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = if (currentScreen == index) colors.primary else unselectedColor
                    )
                }
            }
        }
    }
}
