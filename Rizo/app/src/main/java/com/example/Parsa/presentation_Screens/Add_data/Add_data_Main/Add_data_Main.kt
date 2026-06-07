package com.example.Parsa.presentation_Screens.Add_data.Add_data_Main

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.Parsa.presentation_Screens.Add_data.Costs
import com.example.Parsa.presentation_Screens.Add_data.Debt
import com.example.Parsa.presentation_Screens.Add_data.Income
import com.example.Parsa.presentation_Screens.Add_data.Note
import com.example.Parsa.presentation_Screens.Add_data.Request
import com.example.Parsa.presentation_Screens.Add_data.Savings

@Composable
fun Add_data_Main(
    navController: NavController,
    screen_name: String,
    edit: Boolean = false,
    id: Int = 0,
    viewmodel: Add_dataViewmodel = hiltViewModel()
) {
    when (screen_name) {
        "income" -> Income(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )
        "cost" -> Costs(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )
        "savings" -> Savings(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )

        "note" -> Note(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )
        "request" -> Request(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )
        "debt" -> Debt(
            navController = navController,
            viewModel = viewmodel,
            id = id,
            edit = edit
        )
    }
}