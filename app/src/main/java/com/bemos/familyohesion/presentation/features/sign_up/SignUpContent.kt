package com.bemos.familyohesion.presentation.features.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bemos.familyohesion.domain.models.UserAuth
import com.bemos.familyohesion.shared.utils.ui.button.CustomButton
import com.bemos.familyohesion.shared.utils.ui.text_field.CustomTextField

@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    onRegisterClick: (UserAuth) -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var userRole by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Регистрация",
                fontSize = 40.sp
            )
        }
        Spacer(modifier = Modifier.height(41.dp))
        CustomTextField(
            text = name,
            onValueChange = { name = it },
            label = "Имя"
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            text = email,
            onValueChange = { email = it },
            label = "Почта"
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            text = password,
            onValueChange = { password = it },
            label = "Пароль"
        )
        Spacer(modifier = Modifier.height(14.dp))
        CustomTextField(
            text = userRole,
            onValueChange = { userRole = it },
            label = "Роль (Отец, Мать, Сын, Дочь)"
        )
        Spacer(modifier = Modifier.height(52.dp))
        CustomButton(
            onClick = {
                val user = UserAuth(
                    name = name,
                    email = email,
                    password = password,
                    userRole = userRole
                )
                onRegisterClick(user)
            },
            content = "Регистрация"
        )
    }
}

@Preview
@Composable
private fun SignUpContentPreview() {
    SignUpContent(
        onRegisterClick = {}
    )
}