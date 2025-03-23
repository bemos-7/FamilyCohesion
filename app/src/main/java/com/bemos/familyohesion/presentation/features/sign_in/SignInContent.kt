package com.bemos.familyohesion.presentation.features.sign_in

import androidx.compose.foundation.clickable
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
fun SignInContent(
    onLogInClick: (UserAuth) -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
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
                text = "Вход",
                fontSize = 40.sp
            )
        }
        Spacer(modifier = Modifier.height(41.dp))
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
        Spacer(modifier = Modifier.height(52.dp))
        CustomButton(
            onClick = {
                val userAuth = UserAuth(
                    name = "",
                    email = email,
                    password = password,
                    userRole = ""
                )
                onLogInClick(userAuth)
            },
            content = "Вход"
        )
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                onSignUpClick()
            },
            text = "Нет аккаунта? Регистрация"
        )
    }
}

@Preview
@Composable
private fun SignInContentPreview() {
    SignInContent(
        onLogInClick = {},
        onSignUpClick = {}
    )
}