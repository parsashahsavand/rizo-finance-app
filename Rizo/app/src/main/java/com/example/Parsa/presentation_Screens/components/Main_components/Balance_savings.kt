package com.example.Parsa.presentation_Screens.components.Main_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rizo.finance.R
import com.example.Parsa.presentation_Screens.components.Dialoges.InfoDialog

@Composable
fun Balance_Savings(
    income: String,
    savings: String,
    cost: String,
    request: String,
    debt: String,
    month: String,
    year: String,
) {
    val colors = MaterialTheme.colorScheme
    var showIncome by remember { mutableStateOf(false) }
    var showCost by remember { mutableStateOf(false) }
    var showSavings by remember { mutableStateOf(false) }
    var showRequest by remember { mutableStateOf(false) }
    var showDebt by remember { mutableStateOf(false) }

    var showInfoDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "وضعیت مالی",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(23.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton({ showInfoDialog = true }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "چگونگی عوض کردن تاریخ",
                        tint = colors.primary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(end = 56.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = month + "  -  " + year,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.onSecondary
                    )
                }

                // تسته حالت دوم با نظرسنجی که کدوم بهتره
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                Text(
//                    text = year,
//                    fontSize = 19.sp,
//                    fontWeight = FontWeight.Thin,
//                    color = colors.primary
//                )
            }


            Items_main(
                title = "کل درامد ها",
                digit = income,
                showDigit = showIncome
            )

            Items_main(
                title = "کل هزینه ها",
                digit = cost,
                showDigit = showCost
            )

            Items_main(
                title = "کل پس اندازها",
                digit = savings,
                showDigit = showSavings
            )

            Items_main(
                title = "کل طلب ها",
                digit = request,
                showDigit = showRequest
            )

            Items_main(
                title = "کل بدهی ها",
                digit = debt,
                showDigit = showDebt
            )
        }
    }
    if (showInfoDialog) {
        InfoDialog(
            title = "چطور تاریخ رو عوض کنیم؟",
            decryption = "تاریخ این قسمت رو میتونی از بالای همین صفحه که تاریخ داره عوض کنی.",
            onDismiss = { showInfoDialog = false }
        )
    }
}

@Composable
fun Items_main(
    title: String,
    digit: String,
    showDigit: Boolean
) {
    val colors = MaterialTheme.colorScheme

    var showDigit by remember { mutableStateOf(showDigit) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.background.copy(alpha = 0.45f))
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.primary
            )
            AnimatedContent(showDigit, label = "") {
                Text(
                    text = if (it)
                        digit.replace(",", "")
                            .reversed()
                            .chunked(3)
                            .joinToString(",")
                            .reversed()
                                + "  تومن"
                    else "•••••",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow
                )
            }
        }
        IconButton(onClick = { showDigit = !showDigit }) {
            Icon(
                painter = painterResource(
                    id = if (showDigit) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                ),
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}