package com.example.Parsa.presentation_Screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Date_combo_box(
    label : String,
    items : List<String>,
    size : Float,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

   // var selectedItem by remember { mutableStateOf(items[0]) }

    val colors = MaterialTheme.colorScheme


    Box(
        modifier = Modifier
            .fillMaxWidth(size)
            .padding(5.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = if (expanded || isFocused) colors.primary else colors.secondary,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surface)
    ) {
        OutlinedTextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, fontSize = 16.sp, color = colors.primary) },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.primary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = colors.primary,
                unfocusedLabelColor = colors.secondary,
                cursorColor = colors.primary,
                focusedContainerColor = colors.surface,
                unfocusedContainerColor = colors.surface
            )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable {
                    expanded = !expanded
                    isFocused = expanded
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                isFocused = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface)
        ) {
            items.chunked(2).forEach { pair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    pair.forEach { month ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(colors.surfaceVariant.copy(alpha = 0.40f))
                                .clickable {
                                    onItemSelected(month)
                                  //  selectedItem = month
                                    expanded = false
                                    isFocused = false
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = month,
                                color = colors.primary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        if (pair.size == 1) Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
