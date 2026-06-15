package com.example.Parsa.presentation_Screens.components.Header

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.rizo.finance.R
import com.example.Parsa.presentation_Screens.List.numscreen
import com.example.Parsa.presentation_Screens.List.textserch
import com.example.Parsa.presentation_Screens.components.Add_data_components.MyTextField
import com.example.Parsa.presentation_Screens.components.ChangePassword
import com.example.Parsa.presentation_Screens.components.Dialoges.DialogTwoChoice
import com.example.Parsa.presentation_Screens.components.Dialoges.InfoDialog
import com.example.Parsa.presentation_Screens.components.Dialoges.PasswordOptionsDialog
import com.example.Parsa.presentation_Screens.components.Password
import xyz.teamgravity.pin_lock_compose.PinManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(
    numberScreen: Int,
    viewModel: HeaderViewModel = hiltViewModel()
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    val nithtMode by viewModel.darkMode.collectAsState()
    val activeAI by viewModel.activeAi.collectAsState()
    val numberStars by viewModel.numberStarsFlow.collectAsState()

    var showDialogTwoChoice by remember { mutableStateOf(false) }
    var showDialogTwoChoice1 by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }
    var showInfoDialog1 by remember { mutableStateOf(false) }
    var showPasswordOptionsDialog by remember { mutableStateOf(false) }

    val isSearching = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf(TextFieldValue("")) }

    // آیا دیالوگ PinLock باز است؟
    var showPinDialog by remember { mutableStateOf(false) }

    val pinExists = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        pinExists.value = PinManager.pinExists()
    }


    val currentScreen = if (numscreen == 3) numscreen else numberScreen
    val canSearch = currentScreen == 2

    LaunchedEffect(currentScreen) {
        if (!canSearch) {
            isSearching.value = false
            searchText.value = TextFieldValue("")
        }
    }
    textserch = searchText.value.text
    val textHeader = when (currentScreen) {
        0 -> "گزارش‌های مالی"
        1 -> "ثبت امور مالی"
        2 -> "لیست تراکنش‌ها"
        3 -> "فلیتر ها"
        else -> ""
    }

    CenterAlignedTopAppBar(
        expandedHeight = TopAppBarDefaults.TopAppBarExpandedHeight,
        title = {
            if (isSearching.value && canSearch) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                ) {
                    BasicTextField(
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = colors.onSecondary
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (searchText.value.text.isEmpty()) {
                                    Text(
                                        text = "جستجو...",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = colors.onSecondary
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            } else {
                Text(
                    text = textHeader,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },

        navigationIcon = {
            if (isSearching.value && canSearch) {
                IconButton(onClick = { isSearching.value = false }) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            } else {
                BoxWithConstraints {
                    val screenWidth = maxWidth
                    Box {
                        IconButton(
                            onClick = { expanded = true },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(colors.onSecondary.copy(alpha = 0.1f))
                                .size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Menu",
                                tint = colors.onSecondary
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(screenWidth * 0.8f)
                                .clip(RoundedCornerShape(topEnd = 0.dp, bottomStart = 24.dp))
                                .shadow(8.dp, RoundedCornerShape(24.dp))
                                .background(colors.surface)
                                .padding(vertical = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(245.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            bottomStart = 24.dp,
                                            bottomEnd = 24.dp
                                        )
                                    )
                                    .background(colors.secondaryContainer)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.rizot),
                                    contentDescription = "Menu",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillBounds
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            AnimatedVisibility(visible = expanded) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                ) {
                                    DropdownMenuItem(
                                        text = {
                                            TextforDropdownMenu_a(
                                                text = "راه اندازی صفحه قفل",
                                                icon = Icons.Filled.Lock,
                                                isNight = pinExists.value,
                                                onToggle = {
                                                    if (pinExists.value) {
                                                        showPasswordOptionsDialog = true
                                                    } else {
                                                        showPinDialog = true
                                                    }
                                                }
                                            )
                                        },
                                        onClick = {
                                            expanded = false

                                            if (pinExists.value) {
                                                showPasswordOptionsDialog = true
                                            } else {
                                                showPinDialog = true
                                            }
                                        }

                                    )
                                    Spacer(Modifier.height(7.dp))
                                    TextforDropdownMenu_a(
                                        text = "حالت شب",
                                        icon2 = R.drawable.ic_nightlight,
                                        show_switch = true,
                                        isNight = nithtMode,
                                        onToggle = viewModel::toggleDarkMode
                                    )
                                    Spacer(Modifier.height(7.dp))
                                    TextforDropdownMenu_a(
                                        text = "هوش مصنوعی (ریزو)",
                                        icon2 = R.drawable.ic_robot,
                                        show_switch = true,
                                        isNight = activeAI,
                                        onToggle = viewModel::toggleActiveAi
                                    )
                                    Spacer(Modifier.height(7.dp))
                                    DropdownMenuItem(
                                        text = {
                                            TextforDropdownMenu_a(
                                                text = "مشاوره",
                                                icon = Icons.Filled.Person,
                                                isNight = false,
                                                onToggle = { showInfoDialog1 = true }
                                            )
                                        },
                                        onClick = { expanded = false }
                                    )
                                    Spacer(Modifier.height(7.dp))
                                    DropdownMenuItem(
                                        text = {
                                            TextforDropdownMenu_a(
                                                text = "انتقادات و پیشنهادات",
                                                icon = Icons.Filled.MailOutline,
                                                isNight = false,
                                                onToggle = {
                                                    try {
                                                        // بازار
                                                        val intent = Intent(
                                                            Intent.ACTION_VIEW,
                                                            Uri.parse("bazaar://details?id=${context.packageName}")
                                                        )
                                                        context.startActivity(intent)
                                                    } catch (e: Exception) {
                                                        try {
                                                            // مایکت
                                                            val intent = Intent(
                                                                Intent.ACTION_VIEW,
                                                                Uri.parse("myket://details?id=${context.packageName}")
                                                            )
                                                            context.startActivity(intent)
                                                        } catch (e: Exception) {
                                                            Toast.makeText(
                                                                context,
                                                                "بازار یا مایکت روی دستگاه نصب نیست",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                                    }
                                                }
                                            )
                                        },
                                        onClick = { expanded = false }
                                    )
                                    // برای راهنما
//                                    Spacer(Modifier.height(7.dp))
//                                    DropdownMenuItem(
//                                        text = {
//                                            TextforDropdownMenu_a(
//                                                text = "راهنما",
//                                                icon = Icons.Filled.Info,
//                                                isNight = false,
//                                                onToggle = {
//                                                    TutorialScreen({})
//                                                }
//                                            )
//                                        },
//                                        onClick = { expanded = false }
//                                    )
                                    Spacer(Modifier.height(7.dp))
                                    DropdownMenuItem(
                                        text = {
                                            TextforDropdownMenu_a(
                                                text = "شروع مجدد",
                                                icon = Icons.Filled.Refresh,
                                                isNight = showDialogTwoChoice,
                                                onToggle = { showDialogTwoChoice = true }
                                            )
                                        },
                                        onClick = { expanded = false }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },

        actions = {
            if (canSearch && !isSearching.value) {
                IconButton(onClick = { isSearching.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "ایکن سرچ",
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(colors.onSecondary.copy(alpha = 0.1f))
                            .size(42.dp)
                    )
                }
            } else if (!canSearch) {
                Row(
                    modifier = Modifier
                        .padding(7.dp)
                        .clickable {
                            if (numberStars == 0) showInfoDialog = true else showDialogTwoChoice1 =
                                true
                        }
                        //  .height(45.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(colors.surface.copy(alpha = 0.7f))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = numberStars.toString(),
                        fontSize = 16.sp,
                        color = colors.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(10.dp))
                    Row(horizontalArrangement = Arrangement.End) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color.Yellow,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        },

        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = colors.secondary,
            titleContentColor = colors.onSecondary,
            navigationIconContentColor = colors.onSecondary
        ),
        modifier = Modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .background(colors.secondary)
            .wrapContentHeight()
    )
    if (showDialogTwoChoice) {
        DialogTwoChoice(
            title = "آیا میخواهید برنامه را دوباره راه اندازی کنید؟",
            description = "تمام اطلاعات ذخیره شده حتی اشتراک خریداری شده حذف خواهد شد.",
            onConfirm = {
                val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                am.clearApplicationUserData()

                Toast.makeText(context, "انجام شد", Toast.LENGTH_SHORT).show()
                showDialogTwoChoice = false
            },
            onDismiss = { showDialogTwoChoice = false }
        )
    }
    if (showDialogTwoChoice1) {
        DialogTwoChoice(
            title = "آیا میخواهید ستاره های خود را حذف کنید؟",
            description = "تمام ستاره هایی که با تکمیل چالش ها بدست آوردی حذف میشه و قابل برگشت نیست.",
            onConfirm = {
                viewModel.resetStars()

                Toast.makeText(context, "انجام شد", Toast.LENGTH_SHORT).show()
                showDialogTwoChoice1 = false
            },
            onDismiss = { showDialogTwoChoice1 = false }
        )
    }
    if (showInfoDialog) {
        InfoDialog(
            title = "این ستاره ها چی هستن؟",
            decryption = "برای اینکه خود شما یا فرزند شما برای مدیریت مالی ترقیب بشه میتونه با تعریف اهداف مالی در همین صفحه و تکمیل کردن هر 4 تا چالش یک ستاره دریافت کنه این ستاره ها نشون دهنده پشت کار و هوشمندی شما هست و اینکه چقدر تو زمینه اهداف مالی و مدیریت مالی و رسیدن به اهدافتون توانایی دارید. پس همین حالا برای خودتون یا فرزندتون اهداف مالی مناسب تعریف کنید (از شبکه های اجتماعی ما میتونید یاد بگیرد) و سعی کنید انجامشون بدید.",
            onDismiss = { showInfoDialog = false }
        )
    }
    if (showInfoDialog1) {
        InfoDialog(
            title = "مشاوره",
            decryption = "این بخش در بروزرسانی بعدی فعال میشود.",
            onDismiss = { showInfoDialog1 = false }
        )
    }
    if (showPasswordOptionsDialog) {
        PasswordOptionsDialog(
            onChangePassword = {
                showPasswordOptionsDialog = false
                showPinDialog = true
            },
            onRemovePassword = {
                PinManager.clearPin()
                pinExists.value = false
                showPasswordOptionsDialog = false
            },
            onDismiss = {
                showPasswordOptionsDialog = false
            }
        )
    }
    if (showPinDialog) {
        expanded = false
        Dialog(
            onDismissRequest = { showPinDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            if (pinExists.value) {
                ChangePassword(onSuccess = { showPinDialog = false })
            } else {
                Password(
                    onSuccess = { showPinDialog = false },
                    onActivPin = { pinExists.value = true },
                    ChangePin = true,
                )
            }
        }
    }
}

@Composable
fun TextforDropdownMenu_a(
    text: String,
    icon: ImageVector = Icons.Filled.Home,
    icon2: Int = 1,
    show_switch: Boolean = false,
    isNight: Boolean,
    onToggle: (Boolean) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onToggle(!isNight) }
            .background(colors.surfaceVariant.copy(alpha = 0.40f))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != Icons.Filled.Home) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(26.dp)
            )
        } else {
            Icon(
                painter = painterResource(id = icon2),
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = colors.onSurface
        )

        if (show_switch) {
            Switch(
                checked = isNight,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.primary,
                    uncheckedThumbColor = colors.secondary,
                    checkedTrackColor = colors.primary.copy(alpha = 0.4f),
                    uncheckedTrackColor = colors.secondary.copy(alpha = 0.3f),
                    checkedIconColor = colors.onPrimary,
                    uncheckedBorderColor = colors.secondary
                ),
                modifier = Modifier.scale(0.9f)
            )
        }
    }
}
