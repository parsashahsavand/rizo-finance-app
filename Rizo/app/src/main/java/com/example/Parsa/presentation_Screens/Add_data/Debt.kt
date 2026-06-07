package com.example.Parsa.presentation_Screens.Add_data

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizo.finance.R
import com.example.Parsa.data.local.entity.Debt
import com.example.Parsa.presentation_Screens.Add_data.Add_data_Main.Add_dataViewmodel
import com.example.Parsa.presentation_Screens.components.Add_data_components.MyTextField
import com.example.Parsa.presentation_Screens.components.Header.TextforDropdownMenu_a
import com.example.Parsa.presentation_Screens.components.List_components.TransactionType
import com.example.Parsa.presentation_Screens.components.formatDate
import com.example.Parsa.presentation_Screens.components.persianToMillis
import com.razaghimahdi.compose_persian_date.bottom_sheet.DatePickerLinearModalBottomSheet
import com.razaghimahdi.compose_persian_date.core.components.rememberDialogDatePicker
import kotlinx.coroutines.launch
import saman.zamani.persiandate.PersianDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Debt(
    navController: NavController,
    viewModel: Add_dataViewmodel,
    id: Int = 0,
    edit: Boolean = false,
    type: TransactionType = TransactionType.DEBT
){
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    val purposeState = remember { mutableStateOf(TextFieldValue("")) }
    val titleState = remember { mutableStateOf(TextFieldValue("")) }
    val amountState = remember { mutableStateOf(TextFieldValue("")) }
    var important by remember { mutableStateOf(false) }

    //ممکنه باشه یا نه
   // val descriptionState = remember { mutableStateOf(TextFieldValue("")) }

    val today = remember { PersianDate() }

    val selectedDate = remember {
        mutableStateOf(
            "${today.shYear}/${today.shMonth}/${today.shDay}"
        )
    }

    val selectedDateMillis = remember {
        mutableStateOf(
            persianToMillis(
                today.shYear,
                today.shMonth,
                today.shDay
            )
        )
    }

    val coroutine = rememberCoroutineScope()
    val persianPickerController = rememberDialogDatePicker()
    val bottomSheetState = rememberModalBottomSheetState()

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

    val data by viewModel.data.collectAsState(initial = null)

    LaunchedEffect(id, type) {
        viewModel.load(id, type)
    }

    LaunchedEffect(data, edit) {
        val debt = data as? Debt ?: return@LaunchedEffect
        if (!edit) return@LaunchedEffect

        titleState.value = TextFieldValue(debt.title)
        purposeState.value = TextFieldValue(debt.person)
        amountState.value = TextFieldValue(debt.amount.toString())
        important = debt.isImportant
        selectedDateMillis.value = debt.date
        selectedDate.value = formatDate(debt.date)
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
            Column(modifier = Modifier.padding(9.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(colors.background.copy(alpha = 0.45f))
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "بدهی من به:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.primary
                    )
                    MyTextField(valueState = purposeState, label = "شخص یا سازمان")
                }
                    Spacer(Modifier.height(12.dp))

                    TextforDropdownMenu_a(
                        text = "ایا مهم است؟",
                        icon2 = R.drawable.ic_bookmark_star,
                        show_switch = true,
                        isNight = important,
                        onToggle = { important = it }
                    )
            }
        }
        Spacer(Modifier.height(35.dp))
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
                    text = "اطلاعات اصلی بدهی",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.primary
                )

                MyTextField(valueState = titleState, label = "بابت: مثلا خرید کالا")
                MyTextField(valueState = amountState, label = "مبلغ")

                Spacer(Modifier.height(12.dp))

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
            }
        }
        // توضیحات ممکنه باشه یا نباشه
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            shape = RoundedCornerShape(16.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = colors.surface
//            ),
//            elevation = CardDefaults.cardElevation(6.dp)
//        ) {
//            Column(modifier = Modifier.padding(12.dp)) {
//                Text(
//                    text = "توضیحات یادداشت",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = colors.primary
//                )
//
//                MyTextField(valueState = descriptionState, label = "توضیحات")
//            }
//        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                val amount = amountState.value.text
                    .replace(",", "")
                    .toLongOrNull()

                if (
                    purposeState.value.text.isNotBlank() &&
                    titleState.value.text.isNotBlank() &&
                    amount != null &&
                    selectedDateMillis.value != null
                ) {
                    val insert = Debt(
                        id = if (edit && data is Debt) (data as Debt).id else 0,
                        person = purposeState.value.text,
                        title = titleState.value.text,
                        amount = amount,
                        date = selectedDateMillis.value!!,
                        isImportant = important,
                    )
                    viewModel.addDebt(insert)
                    Toast.makeText(context, "ثبت شد", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "اطلاعات ناقص است", Toast.LENGTH_SHORT).show()
                }
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "ثبت",
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}