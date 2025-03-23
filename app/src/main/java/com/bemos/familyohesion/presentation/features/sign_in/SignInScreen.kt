package com.bemos.familyohesion.presentation.features.sign_in

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.sign_in.vm.SignInViewModel
import com.bemos.familyohesion.presentation.features.sign_in.vm.factory.SignInViewModelFactory

@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModelFactory: SignInViewModelFactory
) {
    val context = LocalContext.current
    val viewModel = viewModel<SignInViewModel>(
        factory = signInViewModelFactory
    )

    val onSuccess by viewModel.onSuccess.collectAsState()
    val onFailure by viewModel.onFailure.collectAsState()

    if (onSuccess) {
        Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show()
        navController.navigate("profile")
    }
    if (onFailure != null) {
        Toast.makeText(context, onFailure!!.message, Toast.LENGTH_SHORT).show()
    }

    SignInContent(
        onLogInClick = { userAuth ->
            viewModel.signIn(
                userAuth
            )
        },
        onSignUpClick = {
            navController.navigate("signUp")
        }
    )
}