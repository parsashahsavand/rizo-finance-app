package com.example.Parsa.presentation_Screens.components.Add_data_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTextField(
    valueState: MutableState<TextFieldValue>,
    label: String,
    modifier: Modifier = Modifier
) {
    var isAmount = false
    if (label == "مبلغ" || label == "از" || label == "تا"){
        isAmount = true
    }

    val isAmount1 = label == "توضیحات"
    var textlength = 35

    when (label){
        "مبلغ" -> textlength = 9
        "از" -> textlength = 9
        "تا" -> textlength = 9
        "توضیحات" -> textlength = 300
    }

    val colors = MaterialTheme.colorScheme

    OutlinedTextField(
        value = valueState.value,
        onValueChange = { input ->
            if (isAmount) {
                val digits = input.text
                    .replace(",", "")
                    .filter { it.isDigit() }
                if (digits.length <= textlength) {
                    val formatted = digits
                        .reversed()
                        .chunked(3)
                        .joinToString(",")
                        .reversed()

                    valueState.value = TextFieldValue(
                        text = formatted,
                        selection = TextRange(formatted.length)
                    )
                }
            } else {
                if (input.text.length <= textlength) {
                    valueState.value = input
                }
            }
        },
        label = { Text(text = label, fontSize = 18.sp) },
        singleLine = !isAmount1,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(if (isAmount1) 260.dp else 70.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = colors.onSurface,
            unfocusedTextColor = colors.onSurface.copy(alpha = 0.9f),
            cursorColor = colors.primary,
            focusedIndicatorColor = colors.primary,
            unfocusedIndicatorColor = colors.onSurface.copy(alpha = 0.12f),
            focusedLabelColor = colors.primary,
            unfocusedLabelColor = colors.onSurface.copy(alpha = 0.6f),
            focusedPlaceholderColor = colors.onSurface.copy(alpha = 0.6f),
            unfocusedPlaceholderColor = colors.onSurface.copy(alpha = 0.4f),
            focusedLeadingIconColor = colors.primary,
            unfocusedLeadingIconColor = colors.onSurface.copy(alpha = 0.6f),
            focusedTrailingIconColor = colors.primary,
            unfocusedTrailingIconColor = colors.onSurface.copy(alpha = 0.6f)
        ),
        keyboardOptions = if (isAmount)
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done)
        else
            KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
    )
}

