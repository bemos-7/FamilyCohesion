package com.bemos.familyohesion.presentation.features.finish_subSkill

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import coil3.compose.rememberAsyncImagePainter
import com.bemos.familyohesion.R
import com.bemos.familyohesion.core.ui.theme.Gray
import com.bemos.familyohesion.domain.models.FamilyMember
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.features.finish_subSkill.util.ui.TimeUi
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.core.ui.theme.LightGray
import com.bemos.familyohesion.core.ui.theme.Red
import com.bemos.familyohesion.core.ui.theme.RedAlpha03
import com.google.api.Context
import kotlinx.coroutines.launch

@Composable
fun FinishSubSkillContent(
    subSkill: SubSkill,
    familyMembers: List<FamilyMember>,
    onEndCLick: (Uri, Int, SubSkill) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    var dropMenuContent by remember {
        mutableStateOf("")
    }
    var isPressedFirst by remember {
        mutableStateOf(false)
    }
    var isPressedSecond by remember {
        mutableStateOf(false)
    }
    var isPressedThird by remember {
        mutableStateOf(false)
    }
    var isPressedFourth by remember {
        mutableStateOf(false)
    }
    var imageUrl by remember { mutableStateOf<Uri?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp)
    ) {
        Spacer(Modifier.height(22.dp))
        Row(
            Modifier.padding(start = 16.dp)
                .clickable {
                    onBackClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_2),
                contentDescription = "back"
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Назад",
                fontSize = 18.sp
            )
        }
        Spacer(Modifier.height(22.dp))

        Spacer(Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(RedAlpha03)
        )

        Spacer(Modifier.height(16.dp))

        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.width(340.dp),
                text = subSkill.name,
                fontSize = 32.sp,
            )
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Укажите напарника. Кто из семьи вам помогал?",
                fontSize = 15.sp
            )
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CardDefaults.shape)
                        .clickable {
                            expanded = true
                        },
                    border = BorderStroke(
                        width = 1.dp, color = LightGray
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 12.dp,
                                bottom = 12.dp,
                                end = 12.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = dropMenuContent,
                            fontSize = 16.sp
                        )
                        Icon(
                            painter = painterResource(R.drawable.round_keyboard_arrow_down_24),
                            contentDescription = "arrow"
                        )
                    }
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    familyMembers.forEach { member ->
                        Text(
                            modifier = Modifier
                                .clickable {
                                    expanded = false
                                    dropMenuContent = "${member.name} (${member.relation})"
                                }
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            text = "${member.name} (${member.relation})"
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
            Text(
                text = "Добавить в семью пользователя",
                fontSize = 16.sp,
                color = Red
            )
            Spacer(Modifier.height(24.dp))
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Gray
                )
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ImageUploader(
                        modifier = Modifier,
                        onUploadComplete = {
                            imageUrl = it
                        },
                        onError = {
                            Toast.makeText(context, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Сколько времени вы провели с семьей?",
                fontSize = 18.sp
            )
            Spacer(Modifier.height(13.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TimeUi(
                    time = 30,
                    isPressed = isPressedFirst,
                    onClick = {
                        isPressedFirst = !isPressedFirst
                    }
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 60,
                    isPressed = isPressedSecond,
                    onClick = {
                        isPressedSecond = !isPressedSecond
                    }
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 90,
                    isPressed = isPressedThird,
                    onClick = {
                        isPressedThird = !isPressedThird
                    }
                )
                Spacer(Modifier.width(12.dp))
                TimeUi(
                    time = 120,
                    isPressed = isPressedFourth,
                    onClick = {
                        isPressedFourth = !isPressedFourth
                    }
                )
            }
            Spacer(Modifier.height(24.dp))
            CustomButton(
                onClick = {
                    calculateTime(
                        context = context,
                        dropMenuContent = dropMenuContent,
                        imageUrl = imageUrl,
                        firstIntervalMinutes = isPressedFirst,
                        secondIntervalMinutes = isPressedSecond,
                        thirdIntervalMinutes = isPressedThird,
                        fourthIntervalMinutes = isPressedFourth,
                        onEndCLick = { time ->
                            if (imageUrl != null) {
                                onEndCLick(imageUrl!!, time, subSkill)
                            } else {
                                Toast.makeText(context, "Пожалуйста, выберите изображение", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                },
                content = "Отправить"
            )
        }
    }
}

fun calculateTime(
    context: android.content.Context,
    dropMenuContent: String,
    imageUrl: Uri?,
    firstIntervalMinutes: Boolean,
    secondIntervalMinutes: Boolean,
    thirdIntervalMinutes: Boolean,
    fourthIntervalMinutes: Boolean,
    onEndCLick: (Int) -> Unit,
) {
    if (dropMenuContent.isNotEmpty()) {
        val time = listOf(
            if (firstIntervalMinutes) 30 else 0,
            if (secondIntervalMinutes) 60 else 0,
            if (thirdIntervalMinutes) 90 else 0,
            if (fourthIntervalMinutes) 120 else 0
        ).sum()
        if (time != 0) {
            onEndCLick(time)
        } else {
            Toast.makeText(context, "Выберите проведенное время", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Выберите члена семьи", Toast.LENGTH_SHORT).show()
    }
    if (imageUrl == null) {
        Toast.makeText(context, "Выберите изображения выполненного задания", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ImageUploader(
    modifier: Modifier = Modifier,
    onUploadComplete: (Uri) -> Unit,
    onError: (Exception) -> Unit = {}
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                imageUri = it
                onUploadComplete(it)
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { galleryLauncher.launch("image/*") },
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            imageUri != null -> {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(120.dp)
                )
            }
            else -> Icon(
                painter = painterResource(R.drawable.round_image_24),
                contentDescription = "Add photo",
                modifier = Modifier.size(100.dp),
                tint = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FinishSubSkillContentPreview() {
    FinishSubSkillContent(
        SubSkill(),
        familyMembers = listOf(
            FamilyMember(
                name = "Some",
                relation = "Something",
                points = 0.0
            )
        ),
        onEndCLick = { string, int, subskill ->

        },
        onBackClick = {}
    )
}