package com.rizo.finance

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.Parsa.presentation_Screens.aa_Navigation.Navigation
import com.example.Parsa.presentation_Screens.components.Footer
import com.example.Parsa.presentation_Screens.components.Header.Header
import com.example.Parsa.presentation_Screens.components.Header.HeaderViewModel
import com.example.Parsa.presentation_Screens.components.Password
import com.example.Parsa.presentation_Screens.components.Teaser
import com.example.Parsa.presentation_Screens.components.TutorialScreen
import com.example.Parsa.ui.theme.CaraTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import xyz.teamgravity.pin_lock_compose.PinManager

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        PinManager.initialize(this)
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val viewModel: HeaderViewModel = hiltViewModel()

            val darkMode by viewModel.darkMode.collectAsState()
            val isThemeLoaded by viewModel.isThemeLoaded.collectAsState()
            val hasSeenTutorial by viewModel.hasSeenTutorial.collectAsState()

            //زمان تیزر
            var showTeaser by remember { mutableStateOf(true) }

            LaunchedEffect(Unit) {
                delay(1500)
                showTeaser = false
            }

            // آیا کاربر احراز هویت شده؟
            var isAuthenticated by remember {
                mutableStateOf(!PinManager.pinExists())
            }

            if (!isThemeLoaded || showTeaser) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Teaser()
                }
                return@setContent
            }

            CaraTheme(darkTheme = darkMode) {
                // اگر PIN هست و هنوز لاگین نکرده
                if (!isAuthenticated) {
                    Password(onSuccess = { isAuthenticated = true })
                } else {
                    // بعد از رمز درست
                    var numberScreen by remember { mutableStateOf(0) }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route

                    LaunchedEffect(currentRoute) {
                        numberScreen = when {
                            currentRoute?.contains("Mian") == true -> 0
                            currentRoute?.contains("Insert") == true -> 1
                            currentRoute?.contains("List") == true -> 2
                            else -> 0
                        }
                    }
                    if (!hasSeenTutorial) {
                        TutorialScreen({
                            viewModel.completeTutorial()
                            Toast.makeText(context, "وارد شدید.", Toast.LENGTH_SHORT).show()
                        }
                    )
                    } else {
                        Scaffold(
                            topBar = { Header(numberScreen) },

                            bottomBar = {
                                Box(
                                    modifier = Modifier.navigationBarsPadding()
                                ) {
                                    Footer(
                                        navController = navController,
                                        currentScreen = numberScreen,
                                        onScreenChange = { numberScreen = it }
                                    )
                                }
                            }

                        ) { innerPadding ->
                            Box(
                                Modifier.padding(innerPadding)
                            ) {
                                Navigation(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
