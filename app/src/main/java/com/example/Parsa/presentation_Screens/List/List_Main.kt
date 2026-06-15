package com.example.Parsa.presentation_Screens.List

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rizo.finance.R
import com.example.Parsa.data.local.entity.toCost
import com.example.Parsa.data.local.entity.toDebt
import com.example.Parsa.data.local.entity.toIncome
import com.example.Parsa.data.local.entity.toNote
import com.example.Parsa.data.local.entity.toRequest
import com.example.Parsa.data.local.entity.toSavings
import com.example.Parsa.presentation_Screens.aa_Navigation.Add_data
import com.example.Parsa.presentation_Screens.aa_Navigation.Filter
import com.example.Parsa.presentation_Screens.aa_Navigation.ViewInformation
import com.example.Parsa.presentation_Screens.components.Dialoges.DialogTwoChoice
import com.example.Parsa.presentation_Screens.components.List_components.Items
import com.example.Parsa.presentation_Screens.components.List_components.TransactionCompactTabs
import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType

var numscreen by mutableStateOf(0)
var textserch by mutableStateOf("")

@Composable
fun List_Main(
    navController: NavHostController,
    viewModel: List_MainViewModel
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsState()

    var selectedTab by remember { mutableStateOf(0) }
    val selectedFilter = remember { mutableStateOf("جدیدترین") }

    var showDialogTwoChoice by remember { mutableStateOf(false) }

    val items by viewModel.transactions.collectAsState()
    val isActiveFilter by viewModel.isActiveFilter.collectAsState()

    LaunchedEffect(viewModel.filter) {
        // بررسی کنید آیا فیلتری اعمال شده است
        val currentFilter = viewModel.filter.value
        val hasActiveFilter = currentFilter.type != null ||
                currentFilter.onlyImportant ||
                currentFilter.onlyRecurring ||
                currentFilter.fromDate != null ||
                currentFilter.toDate != null ||
                currentFilter.minAmount != null ||
                currentFilter.maxAmount != null ||
                currentFilter.category != null ||
                currentFilter.payType != null

        viewModel._isActiveFilter.value = hasActiveFilter
    }

    var itemToDelete by remember { mutableStateOf<TransactionItem?>(null) }

    //فیلتر
    val filteredItems by remember(
        items,
        selectedTab,
        selectedFilter.value,
        textserch
    ) {
        derivedStateOf {
            items
                // فیلتر تب
                .let { list ->
                    when (selectedTab) {
                        1 -> list.filter { it.type == TransactionType.COST }
                        2 -> list.filter { it.type == TransactionType.INCOME }
                        3 -> list.filter { it.type == TransactionType.NOTE }
                        4 -> list.filter { it.type == TransactionType.DEBT }
                        5 -> list.filter { it.type == TransactionType.REQUEST }
                        6 -> list.filter { it.type == TransactionType.SAVINGS }
                        else -> list
                    }
                }

                // سرچ
                .filter {
                    textserch.isBlank() ||
                            it.title.contains(
                                textserch,
                                ignoreCase = true
                            )
                }

                // سورت
                .let { list ->
                    when (selectedFilter.value) {
                        "جدیدترین" ->
                            list.sortedByDescending { it.dateText }

                        "قدیمی‌ترین" ->
                            list.sortedBy { it.dateText }

                        else -> list
                    }
                }
        }
    }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // هدر یه برای فیلتر
        TransactionCompactTabs(
            selectedTab = selectedTab,
            onTabChange = { selectedTab = it },
            selectedItem = selectedFilter.value,
            onItemSelected = { selectedFilter.value = it }
        )

        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = colors.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "در حال بارگزاری...",
                    color = colors.onSurfaceVariant
                )
            }
        } else if (items.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "تراکنشی وجود ندارد.", // متن دلخواه
                    color = colors.onSurfaceVariant // رنگ متن دلخواه
                )
            }

        } else {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Items(
                    items = filteredItems,
                    onItemClick = {
                        navController.navigate(
                            ViewInformation(
                                id = it.id,
                                type = it.type,
                            )
                        )
                    },
                    onEditClick = { item ->
                        when (item.type) {
                            TransactionType.INCOME -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "income",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }

                            TransactionType.COST -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "cost",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }

                            TransactionType.NOTE -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "note",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }

                            TransactionType.DEBT -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "debt",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }

                            TransactionType.SAVINGS -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "savings",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }

                            TransactionType.REQUEST -> {
                                navController.navigate(
                                    Add_data(
                                        screen_name = "request",
                                        edit = true,
                                        id = item.id
                                    )
                                )
                            }
                        }
                    },
                    onDeleteClick = { item ->
                        itemToDelete = item
                        showDialogTwoChoice = true
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, bottom = 15.dp, top = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        IconButton(
            onClick = {
                if (isActiveFilter) {
                    viewModel.clearFilter()
                } else {
                    navController.navigate(Filter)
                }
            },
            modifier = Modifier.size(100.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.filter),
                contentDescription = if (isActiveFilter) "حذف فیلتر" else "اعمال فیلتر",
                tint = if (!isActiveFilter) colors.onSecondary else Color.Red,
                modifier = Modifier.size(60.dp)
            )
        }
    }

    if (showDialogTwoChoice && itemToDelete != null) {
        DialogTwoChoice(
            title = "آیا از حذف این مورد اطمینان دارید؟",
            onConfirm = {
                when (itemToDelete!!.type) {
                    TransactionType.COST ->
                        viewModel.deleteCost(itemToDelete!!.toCost())

                    TransactionType.INCOME ->
                        viewModel.deleteIncome(itemToDelete!!.toIncome())

                    TransactionType.NOTE ->
                        viewModel.deleteNote(itemToDelete!!.toNote())

                    TransactionType.SAVINGS ->
                        viewModel.deleteSavings(itemToDelete!!.toSavings())

                    TransactionType.DEBT ->
                        viewModel.deleteDebt(itemToDelete!!.toDebt())

                    TransactionType.REQUEST ->
                        viewModel.deleteRequest(itemToDelete!!.toRequest())
                }

                Toast.makeText(context, "حذف شد", Toast.LENGTH_SHORT).show()
                showDialogTwoChoice = false
                itemToDelete = null
            },
            onDismiss = {
                showDialogTwoChoice = false
                itemToDelete = null
            }
        )
    }

}

