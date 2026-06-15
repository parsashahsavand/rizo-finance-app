package com.example.Parsa.presentation_Screens.aa_Navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.Parsa.presentation_Screens.Add_data.Add_data_Main.Add_data_Main
import com.example.Parsa.presentation_Screens.Filter.Filter_Main
import com.example.Parsa.presentation_Screens.Insert.Insert_Main
import com.example.Parsa.presentation_Screens.List.List_Main
import com.example.Parsa.presentation_Screens.List.List_MainViewModel
import com.example.Parsa.presentation_Screens.Main_Screen.MainScreen
import com.example.Parsa.presentation_Screens.ViewInformation.ViewInformation_Main


@Composable
fun Navigation(navController: NavHostController,viewModel: List_MainViewModel = hiltViewModel()) {
    NavHost(
        navController = navController,
        startDestination = Mian,
    ) {
        composable<Mian> {
            MainScreen(navController)
        }

        composable<Insert> {
            Insert_Main(navController)
        }

        composable<List> {
            List_Main(navController,viewModel)
        }

        composable<Add_data> {
            val args = it.toRoute<Add_data>()
            Add_data_Main(
                navController = navController,
                screen_name = args.screen_name,
                edit = args.edit,
                id = args.id
            )
        }

        composable<Filter> {
            Filter_Main(navController,viewModel)
        }

        composable<ViewInformation> {
            val args = it.toRoute<ViewInformation>()
            ViewInformation_Main(id = args.id, type = args.type)
        }
    }
}