package com.example.Parsa.presentation_Screens.components.Main_components.Added_to_homepage

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.Parsa.data.local.entity.toCost
import com.example.Parsa.data.local.entity.toIncome
import com.example.Parsa.presentation_Screens.aa_Navigation.Add_data
import com.example.Parsa.presentation_Screens.aa_Navigation.ViewInformation
import com.example.Parsa.presentation_Screens.components.Dialoges.DialogTwoChoice
import com.example.Parsa.presentation_Screens.components.Dialoges.InfoDialog
import com.example.Parsa.presentation_Screens.components.List_components.Items
import com.example.Parsa.presentation_Screens.components.List_components.TransactionItem
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType

@Composable
fun Added_to_homepage(
    navController: NavController,
    viewModel: Added_to_homepageViewModel = hiltViewModel()
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var showInfoDialog by remember { mutableStateOf(false) }

    val items by viewModel.transactions.collectAsState()
    var itemToDelete by remember { mutableStateOf<TransactionItem?>(null) }
    var showDialogTwoChoice by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(if (items.isEmpty()) 70.dp else 400.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // عنوان
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 7.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "تراکنش های اضافه شده به صفحه اصلی",
                        color = colors.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                IconButton({ showInfoDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "ایکون تراکنش های افزوده شده به صفحه اصلی",
                        tint = colors.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize()
                .weight(1f)
            ) {
                Items(
                    items = items,
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

                            TransactionType.NOTE -> TODO()
                            TransactionType.DEBT -> TODO()
                            TransactionType.REQUEST -> TODO()
                            TransactionType.SAVINGS -> TODO()
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
    if (showInfoDialog) {
        InfoDialog(
            title = "درباره این تراکنش ها!",
            decryption = "در صفحه اضافه کردن هزینه و درآمد گزینه ای در بخش تنظیمات وجود داره که با فعال کردن اون اون تراکنش به این بخش اضافه میشه برای دسترسی راحت تر میشه.",
            onDismiss = { showInfoDialog = false }
        )
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

                    TransactionType.NOTE -> TODO()
                    TransactionType.SAVINGS -> TODO()
                    TransactionType.DEBT -> TODO()
                    TransactionType.REQUEST -> TODO()
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