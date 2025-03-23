package com.bemos.familyohesion.presentation.features.sign_up

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.sign_up.vm.SignUpViewModel
import com.bemos.familyohesion.presentation.features.sign_up.vm.factory.SignUpViewModelFactory

@Composable
fun SignUpScreen(
    navController: NavController,
    signUpViewModelFactory: SignUpViewModelFactory
) {
    val context = LocalContext.current

    val viewModel = viewModel<SignUpViewModel>(
        factory = signUpViewModelFactory
    )

    val onSuccess by viewModel.onSuccess.collectAsState()
    val onFailure by viewModel.onFailure.collectAsState()

    if (onSuccess) {
        Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show()
        navController.navigate("signIn")
    }
    if (onFailure != null) {
        Toast.makeText(context, onFailure!!.message, Toast.LENGTH_SHORT).show()
    }

    SignUpContent(
        onRegisterClick = { userAuth ->
            viewModel.signUp(
                userAuth
            )
        },
        onSignInClick = {
            navController.navigate("signIn")
        }
    )
}