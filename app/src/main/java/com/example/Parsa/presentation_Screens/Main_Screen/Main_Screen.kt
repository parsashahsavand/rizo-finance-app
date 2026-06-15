package com.example.Parsa.presentation_Screens.Main_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.Parsa.presentation_Screens.components.Date_combo_box
import com.example.Parsa.presentation_Screens.components.Main_components.Added_to_homepage.Added_to_homepage
import com.example.Parsa.presentation_Screens.components.Main_components.Ai
import com.example.Parsa.presentation_Screens.components.Main_components.Balance_Savings
import com.example.Parsa.presentation_Screens.components.Main_components.BarChart
import com.example.Parsa.presentation_Screens.components.Main_components.Challenges.Challenges
import com.example.Parsa.presentation_Screens.components.Main_components.PieChart1
import com.example.Parsa.presentation_Screens.components.Main_components.Select_Chart

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val activeAi = viewModel.activeAi.collectAsState()

    val items_month = listOf(
        "فروردین", "اردیبهشت", "خرداد", "تیر",
        "مرداد", "شهریور", "مهر", "آبان",
        "آذر", "دی", "بهمن", "اسفند"
    )
    val items_year = listOf("1404","1405","1406","1407")
    val radioItems = listOf("نمودار سالیانه", "نمودار ماهانه")

    val selected = remember { mutableStateOf("نمودار ماهانه") }

    val selectedMonth = remember { mutableStateOf("فروردین") }
    val selectedYear = remember { mutableStateOf("1405") }

    val month = when (selectedMonth.value) {
        "فروردین" -> 1
        "اردیبهشت" -> 2
        "خرداد" -> 3
        "تیر" -> 4
        "مرداد" -> 5
        "شهریور" -> 6
        "مهر" -> 7
        "آبان" -> 8
        "آذر" -> 9
        "دی" -> 10
        "بهمن" -> 11
        "اسفند" -> 12
        else -> 1
    }
    val year = selectedYear.value.toInt()

    LaunchedEffect(month, year) {
        viewModel.loadTotalCosts(year, month)
        viewModel.loadTotalIncome(year, month)
        viewModel.loadTotalSavings(year, month)
        viewModel.loadTotalDebt(year, month)
        viewModel.loadTotalRequests(year, month)
    }

    val total_cost = viewModel.totalCosts.collectAsState()
    val total_income = viewModel.totalIncome.collectAsState()
    val total_savings = viewModel.totalSavings.collectAsState()
    val total_debt = viewModel.totalDebt.collectAsState()
    val total_requests = viewModel.totalRequests.collectAsState()

    //برای بروزرسانی هوش مصنوعی
    // var showDialogTwoChoice by remember { mutableStateOf(false) }

    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Box(modifier = Modifier.weight(1f)) {
                Date_combo_box(
                    label = "انتخاب ماه",
                    items = items_month,
                    size = 1f,
                    selectedItem = selectedMonth.value,
                    onItemSelected = { selectedMonth.value = it }
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                Date_combo_box(
                    label = "انتخاب سال",
                    items = items_year,
                    size = 1f,
                    selectedItem = selectedYear.value,
                    onItemSelected = { selectedYear.value = it }
                )
            }
        }

        if (selected.value == "نمودار سالیانه")
            BarChart(
                viewModel = viewModel,
                selectedYear = selectedYear.value.toInt()
            )
        else
            PieChart1(
                viewModel = viewModel,
                monthName = selectedMonth.value,
                selectedYear = selectedYear.value.toInt(),
                selectedMonth = month
            )

        Select_Chart(selected = selected, items = radioItems)

        Balance_Savings(
            income = total_income.value.toString(),
            cost = total_cost.value.toString(),
            savings = total_savings.value.toString(),
            request = total_requests.value.toString(),
            debt = total_debt.value.toString(),
            month = selectedMonth.value,
            year = selectedYear.value
        )

        if (activeAi.value)
            Ai(
                income = total_income.value.toString(),
                cost = total_cost.value.toString(),
                savings = total_savings.value.toString(),
                request = total_requests.value.toString(),
                debt = total_debt.value.toString(),
                month = selectedMonth.value,
                year = selectedYear.value
            )

      //  Socialnetworks()

        Challenges()

        Added_to_homepage(navController)
    }

//    if (showDialogTwoChoice){
//        DialogTwoChoice(
//            title = "بروزرسانی پیشنهاد هوش مصنوعی",
//            description = "با بروزرسانی هوش مصنوعی یه استر از شما مصرف میشود و پیشنهاد جدیدی به شما داده میشود.",
//            onConfirm = {/* بروزرسانی هوش مصنوعی اینجا اتفاق می افته */},
//            onDismiss = { showDialogTwoChoice = false }
//        )
//    }
}