package com.bemos.familyohesion.presentation.features.edit_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bemos.familyohesion.R
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.shared.utils.ui.text_field.CustomTextField

@Composable
fun EditProfileContent(
    user: User,
    onSaveClick: (User) -> Unit,
    onResetPassClick: (String) -> Unit
) {
    var isShow by remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf(user.name)
    }
    var email by remember {
        mutableStateOf(user.email)
    }

    if (isShow) {
        Dialog(
            onDismissRequest = {
                isShow = false
            }
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 30.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(
                            R.drawable.email
                        ),
                        contentDescription = "email",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Проверьте Ваш Email",
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Мы отправили письмо для восстановления пароля на вашу электронную почту.",
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 50.dp),
        verticalArrangement = Arrangement.Center
    ) {
        CustomTextField(
            text = name,
            onValueChange = {
                name = it
            },
            label = "Имя"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = email,
            onValueChange = {
                email = it
            },
            label = "Почта"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            text = "**********",
            onValueChange = {

            },
            label = "Пароль"
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomButton(
            onClick = {
                isShow = true
                onResetPassClick(user.email)
            },
            content = "Сброс пароля"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        CustomButton(
            onClick = {
                val newUser = User(
                    userId = user.userId,
                    name = name,
                    email = email
                )
                onSaveClick(newUser)
            },
            content = "Сохранить"
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview
@Composable
private fun EditProfileContentPreview() {
    EditProfileContent(
        user = User(),
        onSaveClick = {},
        onResetPassClick = {}
    )
}