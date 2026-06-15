package com.example.Parsa.presentation_Screens.components.Main_components.Challenges

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.Parsa.data.local.entity.Challenge
import com.example.Parsa.presentation_Screens.components.Add_data_components.MyTextField
import com.example.Parsa.presentation_Screens.components.Dialoges.DialogTwoChoice
import com.example.Parsa.presentation_Screens.components.Dialoges.InfoDialog

@Composable
fun Challenges(viewModel: ChallengesViewModel = hiltViewModel()) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var showInfoDialog by remember { mutableStateOf(false) }
    var showDialogTwoChoice by remember { mutableStateOf(false) }

    val challenges by viewModel.allChallenges.collectAsState(initial = emptyList())
    val existingChallenge = challenges.firstOrNull()

    val textChallenge1 = remember { mutableStateOf(TextFieldValue("")) }
    val textChallenge2 = remember { mutableStateOf(TextFieldValue("")) }
    val textChallenge3 = remember { mutableStateOf(TextFieldValue("")) }
    val textChallenge4 = remember { mutableStateOf(TextFieldValue("")) }

    val checkChallenge1 = remember { mutableStateOf(false) }
    val checkChallenge2 = remember { mutableStateOf(false) }
    val checkChallenge3 = remember { mutableStateOf(false) }
    val checkChallenge4 = remember { mutableStateOf(false) }

    // یه state برای نگهداری آخرین مقدار ذخیره شده
    var lastSaved by remember { mutableStateOf<Challenge?>(null) }

    //اسلایدر
    val progress = remember {
        derivedStateOf {
            val checkedCount = listOf(
                checkChallenge1.value,
                checkChallenge2.value,
                checkChallenge3.value,
                checkChallenge4.value
            ).count { it == true }

            checkedCount / 4f
        }
    }

    LaunchedEffect(existingChallenge) {
        existingChallenge?.let { challenge ->
            textChallenge1.value = TextFieldValue(challenge.textChallenge1)
            textChallenge2.value = TextFieldValue(challenge.textChallenge2)
            textChallenge3.value = TextFieldValue(challenge.textChallenge3)
            textChallenge4.value = TextFieldValue(challenge.textChallenge4)

            checkChallenge1.value = challenge.checkChallenge1
            checkChallenge2.value = challenge.checkChallenge2
            checkChallenge3.value = challenge.checkChallenge3
            checkChallenge4.value = challenge.checkChallenge4

            lastSaved = Challenge(
                id = challenge.id,
                textChallenge1 = challenge.textChallenge1,
                textChallenge2 = challenge.textChallenge2,
                textChallenge3 = challenge.textChallenge3,
                textChallenge4 = challenge.textChallenge4,
                checkChallenge1 = challenge.checkChallenge1,
                checkChallenge2 = challenge.checkChallenge2,
                checkChallenge3 = challenge.checkChallenge3,
                checkChallenge4 = challenge.checkChallenge4
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        colors.primaryContainer,
                        colors.tertiaryContainer
                    )
                )
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(23.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "اهداف مالی",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colors.onPrimaryContainer
            )

            IconButton({ showInfoDialog = true }) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "درباره چالش ها",
                    tint = colors.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
        Text(
            text = "اهداف مالیت رو بنویس و فقط انجامشون بده.",
            fontSize = 12.sp,
            color = colors.onPrimaryContainer.copy(alpha = 0.7f)
        )

        Spacer(Modifier.height(16.dp))


        // سطح و اسلایدر
        LevelProgress(
            current = progress.value,
            challenge_done = (progress.value * 4).toInt()
        )

        Spacer(Modifier.height(20.dp))

        // لیست چالش‌ها
        ChallengeItem(
            valueState = textChallenge1,
            done = checkChallenge1,
            labes = "چالش 1:"
        )
        ChallengeItem(
            valueState = textChallenge2,
            done = checkChallenge2,
            labes = "چالش 2:"
        )
        ChallengeItem(
            valueState = textChallenge3,
            done = checkChallenge3,
            labes = "چالش 3:"
        )
        ChallengeItem(
            valueState = textChallenge4,
            done = checkChallenge4,
            labes = "چالش 4:"
        )

        OutlinedButton(
            onClick = {
                val currentChallenge = Challenge(
                    id = 1,
                    textChallenge1 = textChallenge1.value.text,
                    textChallenge2 = textChallenge2.value.text,
                    textChallenge3 = textChallenge3.value.text,
                    textChallenge4 = textChallenge4.value.text,
                    checkChallenge1 = checkChallenge1.value,
                    checkChallenge2 = checkChallenge2.value,
                    checkChallenge3 = checkChallenge3.value,
                    checkChallenge4 = checkChallenge4.value
                )

                // چک کن اگر با آخرین ذخیره شده فرق داره
                if (currentChallenge != lastSaved) {
                    viewModel.upsertChallenge(currentChallenge)
                    Toast.makeText(context, "ذخیره شد.", Toast.LENGTH_SHORT).show()
                    lastSaved = currentChallenge
                } else {
                    Toast.makeText(context, "لیست یا خالی است یا تکراری!", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, colors.onSecondary),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = colors.onSecondary
            )
        ) {
            Text("ذخیره کن.", fontSize = 18.sp)
        }
        if (checkChallenge1.value && checkChallenge2.value && checkChallenge3.value && checkChallenge4.value) {
            OutlinedButton(
                onClick = { showDialogTwoChoice = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, colors.surfaceDim),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.surfaceDim
                )
            ) {
                Text("⭐ تبریک! ستاره بگیرید.", fontSize = 18.sp)
            }
        }

    }
    if (showInfoDialog) {
        InfoDialog(
            title = "راجب اهداف مالی",
            decryption = "اینجا می تونی هدف های مالیت رو مثل یه بازی وارد کنی و هر بار که جلو میری، وضعیت مالیت بهتر میشه! دیدن پیشرفتت باعث می شه انگیزه بگیری و خیلی سریع تر به چیزایی که می خوای برسی. این بخش کمک می کنه خرج هات رو بهتر کنترل کنی و مدیریت مالی برات ساده تر و حتی فان تر بشه!",
            onDismiss = { showInfoDialog = false }
        )
    }
    //برای ستاره
    if (showDialogTwoChoice) {
        DialogTwoChoice(
            title = "آیا از گرفتن ستاره اطمینان دارید؟",
            description = "با گرفتن ستاره همه اهداف مالی شما پاک(ریست) مشود. و یک ستاره به شما اضافه خواهد شد.",
            onConfirm = {
                val currentChallenge = Challenge(
                    id = 1,
                    textChallenge1 = "",
                    textChallenge2 = "",
                    textChallenge3 = "",
                    textChallenge4 = "",
                    checkChallenge1 = false,
                    checkChallenge2 = false,
                    checkChallenge3 = false,
                    checkChallenge4 = false
                )

                if (lastSaved == currentChallenge){
                    Toast.makeText(context, "لطفا اول تغییرات را ذخیره کنید.", Toast.LENGTH_SHORT).show()
                } else if (lastSaved == null){
                    Toast.makeText(context, "لطفا اول تغییرات را ذخیره کنید.", Toast.LENGTH_SHORT).show()
                } else{
                    viewModel.upsertChallenge(currentChallenge)
                    Toast.makeText(context, "یک ⭐ به شما اضافه شد.", Toast.LENGTH_SHORT).show()
                    lastSaved = currentChallenge
                    showDialogTwoChoice = false
                    viewModel.incrementStars()
                }
            },
            onDismiss = { showDialogTwoChoice = false }
        )
    }
}

@Composable
fun LevelProgress(current: Float, challenge_done: Int) {
    val colors = MaterialTheme.colorScheme

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("0", color = colors.onPrimaryContainer, fontSize = 14.sp)
            Text("4", color = colors.onPrimaryContainer, fontSize = 14.sp)
        }

        Slider(
            value = current,
            onValueChange = {},
            enabled = false,
            colors = SliderDefaults.colors(
                disabledThumbColor = colors.primary,
                disabledActiveTrackColor = colors.primary,
                disabledInactiveTrackColor = colors.onPrimaryContainer.copy(alpha = 0.3f)
            )
        )

        Text(
            text = "$challenge_done از 4 چالش انجام شده",
            fontSize = 12.sp,
            color = colors.onPrimaryContainer.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ChallengeItem(
    valueState: MutableState<TextFieldValue>,
    done: MutableState<Boolean>,
    labes: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween  // فاصله بین تکست فیلد و رادیو
    ) {
        MyTextField(
            valueState = valueState,
            label = labes,
            modifier = Modifier.weight(1f)  // تکست فیلد فضای باقی‌مونده رو بگیره
        )

        Checkbox(
            checked = done.value,
            onCheckedChange = {
                done.value = it
            },
            enabled = if (valueState.value.text != "") true else false
        )
}

Spacer(Modifier.height(10.dp))
}
