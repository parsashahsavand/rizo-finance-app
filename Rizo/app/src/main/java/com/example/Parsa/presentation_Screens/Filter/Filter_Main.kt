package com.example.Parsa.presentation_Screens.Filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.rizo.finance.R
import com.example.Parsa.presentation_Screens.List.List_MainViewModel
import com.example.Parsa.presentation_Screens.List.numscreen
import com.example.Parsa.presentation_Screens.aa_Navigation.List
import com.example.Parsa.presentation_Screens.components.Add_data_components.MyTextField
import com.example.Parsa.presentation_Screens.components.Date_combo_box
import com.example.Parsa.presentation_Screens.components.Header.TextforDropdownMenu_a
import com.example.Parsa.presentation_Screens.components.List_components.TransactionCompactTabs
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import com.example.Parsa.presentation_Screens.components.persianToMillis
import com.razaghimahdi.compose_persian_date.bottom_sheet.DatePickerLinearModalBottomSheet
import com.razaghimahdi.compose_persian_date.core.components.rememberDialogDatePicker
import kotlinx.coroutines.launch

data class TransactionFilter(
    val type: TransactionType? = null,
    val fromDate: Long? = null,
    val toDate: Long? = null,
    val minAmount: Long? = null,
    val maxAmount: Long? = null,
    val category: String? = null,
    val payType: String? = null,
    val onlyImportant: Boolean = false,
    val onlyRecurring: Boolean = false
) {
    // متد برای بررسی اینکه فیلتر خالی/پیش‌فرض است
    fun isEmpty(): Boolean {
        return type == null &&
                !onlyImportant &&
                !onlyRecurring &&
                fromDate == null &&
                toDate == null &&
                minAmount == null &&
                maxAmount == null &&
                category == null &&
                payType == null
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Filter_Main(
    navController: NavHostController,
    viewModel: List_MainViewModel
) {
    val colors = MaterialTheme.colorScheme
    val amountState1 = remember { mutableStateOf(TextFieldValue("")) }
    val amountState2 = remember { mutableStateOf(TextFieldValue("")) }

    var selectedTab by remember { mutableStateOf(0) }

    val selectedDate = remember { mutableStateOf("تاریخ انتخاب نشده") }
    val selectedDateMillis = remember { mutableStateOf<Long?>(null) }
    val selectedDate2 = remember { mutableStateOf("تاریخ انتخاب نشده") }
    val selectedDateMillis2 = remember { mutableStateOf<Long?>(null) }

    var important by remember { mutableStateOf(false) }
    var repetition by remember { mutableStateOf(false) }

    val selectedCategory = remember { mutableStateOf("پیش فرض") }
    val selectedPayType = remember { mutableStateOf("پیش فرض") }

    val categories =
        when (selectedTab) {
            0 -> listOf(
                "خوراک", "حمل‌ونقل", "قبض‌ها", "تفریح", "سلامت", "دیگر", "حقوق ماهانه",
                "پاداش",
                "فروش",
                "کار فریلنس",
                "کمک هزینه/هدیه",
                "سود سرمایه گذاری",
                "اجاره دریافتی",
                "سایر درآمدها"
            )

            1 -> listOf("خوراک", "حمل‌ونقل", "قبض‌ها", "تفریح", "سلامت", "دیگر")
            2 -> listOf(
                "حقوق ماهانه",
                "پاداش",
                "فروش",
                "کار فریلنس",
                "کمک هزینه/هدیه",
                "سود سرمایه گذاری",
                "اجاره دریافتی",
                "سایر درآمدها"
            )

            else -> listOf()
        }


    val payTypes = listOf("نقدی", "کارت", "انتقال", "آنلاین")


    // تاریخ اولی
    val coroutine = rememberCoroutineScope()
    val persianPickerController = rememberDialogDatePicker()
    val bottomSheetState = rememberModalBottomSheetState()

    // تاریخ دومی
    val coroutine2 = rememberCoroutineScope()
    val persianPickerController2 = rememberDialogDatePicker()
    val bottomSheetState2 = rememberModalBottomSheetState()

    // هدر صفحه
    DisposableEffect(Unit) {
        numscreen = 3

        onDispose {
            numscreen = 0
        }
    }

    if (bottomSheetState.isVisible) {
        DatePickerLinearModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState,
            controller = persianPickerController,
            onDismissRequest = {
                coroutine.launch { bottomSheetState.hide() }
            },
            onDateChanged = { year, month, day ->
                selectedDate.value = "$year/$month/$day"
                selectedDateMillis.value = persianToMillis(year, month, day)
            }
        )
    }

    if (bottomSheetState2.isVisible) {
        DatePickerLinearModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = bottomSheetState2,
            controller = persianPickerController2,
            onDismissRequest = {
                coroutine2.launch { bottomSheetState2.hide() }
            },
            onDateChanged = { year, month, day ->
                selectedDate2.value = "$year/$month/$day"
                selectedDateMillis2.value = persianToMillis(year, month, day)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colors.surface
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "تاریخ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )
                Spacer(Modifier.height(15.dp))

                Text(
                    text = "از تاریخ...",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.secondaryContainer)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDate.value,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = { coroutine.launch { bottomSheetState.show() } }
                    ) {
                        Text("انتخاب تاریخ")
                    }
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "تا تاریخ...",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.secondaryContainer)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedDate2.value,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Button(
                        onClick = { coroutine.launch { bottomSheetState2.show() } }
                    ) {
                        Text("انتخاب تاریخ")
                    }
                }

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "نوع تراکنش",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )

                Spacer(Modifier.height(15.dp))

                TransactionCompactTabs(
                    box = false,
                    selectedTab = selectedTab,
                    onTabChange = { selectedTab = it }
                )

                Spacer(Modifier.height(40.dp))

                if (selectedTab == 0 || selectedTab == 1 || selectedTab == 2) {
                    Text(
                        text = "دسته بندی",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )

                    Spacer(Modifier.height(15.dp))

                    Date_combo_box(
                        label = "دسته‌بندی",
                        items = categories,
                        size = 1f,
                        selectedItem = selectedCategory.value,
                        onItemSelected = { selectedCategory.value = it }
                    )
                    Spacer(Modifier.height(40.dp))
                }
                if (selectedTab != 3) {
                    Text(
                        text = "محدوده مبلغ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )

                    Spacer(Modifier.height(15.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            MyTextField(valueState = amountState1, label = "از")
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            MyTextField(valueState = amountState2, label = "تا")
                        }
                    }

                    Spacer(Modifier.height(40.dp))
                }

                if (selectedTab == 1) {
                    Text(
                        text = "نوع پرداخت",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.primary
                    )

                    Spacer(Modifier.height(15.dp))


                    Date_combo_box(
                        label = "نوع پرداخت",
                        items = payTypes,
                        size = 1f,
                        selectedItem = selectedPayType.value,
                        onItemSelected = { selectedPayType.value = it }
                    )
                    Spacer(Modifier.height(40.dp))
                }

                Text(
                    text = "وضعیت",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )

                Spacer(Modifier.height(15.dp))

                TextforDropdownMenu_a(
                    text = "فقط مهم ها رو نشون بده",
                    icon2 = R.drawable.ic_bookmark_star,
                    show_switch = true,
                    isNight = important,
                    onToggle = { important = it }
                )

                if (selectedTab == 1 || selectedTab == 2 || selectedTab == 0) {
                    Spacer(Modifier.height(12.dp))

                    TextforDropdownMenu_a(
                        text = "فقط اضافه شده ها به صفحه اصلی رو نشون بده",
                        icon = Icons.Filled.Refresh,
                        show_switch = true,
                        isNight = repetition,
                        onToggle = { repetition = it }
                    )
                }
                Spacer(Modifier.height(40.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // دکمه لغو
                    OutlinedButton(
                        onClick = { navController.navigate(List) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(
                            width = 1.5.dp,
                            color = Color(0xFFD32F2F) // قرمز ملایم
                        ),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Text(
                            text = "لغو",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    // دکمه ثبت
                    Button(
                        onClick = {
                            viewModel.applyFilter(
                                TransactionFilter(
                                    type = when (selectedTab) {
                                        1 -> TransactionType.COST
                                        2 -> TransactionType.INCOME
                                        3 -> TransactionType.NOTE
                                        4 -> TransactionType.DEBT
                                        5 -> TransactionType.REQUEST
                                        6 -> TransactionType.SAVINGS
                                        else -> null
                                    },
                                    fromDate = selectedDateMillis.value,
                                    toDate = selectedDateMillis2.value,
                                    minAmount = amountState1.value.text.toLongOrNull(),
                                    maxAmount = amountState2.value.text.toLongOrNull(),
                                    category = selectedCategory.value.takeIf { it != "پیش فرض" },
                                    payType = selectedPayType.value.takeIf { it != "پیش فرض" },
                                    onlyImportant = important,
                                    onlyRecurring = repetition
                                )
                            )
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF8F),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 2.dp,
                            pressedElevation = 1.dp
                        )
                    ) {
                        Text(
                            text = "اعمال",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}