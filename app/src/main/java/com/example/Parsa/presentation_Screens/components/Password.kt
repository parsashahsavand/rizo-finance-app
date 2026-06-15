package com.example.Parsa.presentation_Screens.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.teamgravity.pin_lock_compose.ChangePinLock
import xyz.teamgravity.pin_lock_compose.PinLock

@Composable
fun Password(
    onSuccess: () -> Unit,
    onActivPin: () -> Unit = {},
    ChangePin: Boolean = false
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    PinLock(
        title = { pinExists ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (pinExists) "ورود با رمز عبور" else "ایجاد رمز عبور",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (pinExists)
                        "برای ادامه رمز خود را وارد کنید"
                    else
                        "یک رمز امن برای برنامه تعیین کنید",
                    fontSize = 13.sp,
                    color = colors.onSurfaceVariant
                )
            }
        },

        onPinCorrect = {
            onSuccess()
            Toast.makeText(context, "وارد شدید.", Toast.LENGTH_SHORT).show()
        },

        onPinIncorrect = {
            Toast
                .makeText(context, "رمز وارد شده نادرست است", Toast.LENGTH_SHORT)
                .show()
        },

        onPinCreated = {
            Toast
                .makeText(context, "رمز عبور با موفقیت ایجاد شد", Toast.LENGTH_SHORT)
                .show()
            onActivPin()
            onSuccess()
        },
        backgroundColor = colors.surface,
        textColor = colors.primary,
    )
    if (ChangePin) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, bottom = 15.dp, top = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            IconButton(
                onClick = { onSuccess() },
                modifier = Modifier.size(100.dp)
            ) {
                Text(
                    text = "خروج",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ChangePassword(onSuccess: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    ChangePinLock(
        title = { authenticated ->
            Text(
                text = if (authenticated) "ایجاد رمز عبور جدید" else "رمز عبور قبلی خود را وارد کنید",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onSurface
            )
        },
        onPinIncorrect = {
            Toast
                .makeText(context, "رمز وارد شده نادرست است", Toast.LENGTH_SHORT)
                .show()
        },
        onPinChanged = {
            Toast
                .makeText(context, "رمز عبور با موفقیت ایجاد شد", Toast.LENGTH_SHORT)
                .show()
            onSuccess()
        },
        backgroundColor = colors.surface,
        textColor = colors.primary,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, bottom = 15.dp, top = 10.dp, end = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        IconButton(
            onClick = { onSuccess() },
            modifier = Modifier.size(100.dp)
        ) {
            Text(
                text = "خروج",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier
                    .background(Color.Transparent)
                    .padding(bottom = 10.dp)
            )
        }
    }
}
