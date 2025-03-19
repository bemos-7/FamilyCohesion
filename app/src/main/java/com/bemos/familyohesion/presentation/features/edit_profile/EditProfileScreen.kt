package com.bemos.familyohesion.presentation.features.edit_profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.presentation.features.edit_profile.vm.EditProfileViewModel
import com.bemos.familyohesion.presentation.features.edit_profile.vm.factory.EditProfileViewModelFactory

@Composable
fun EditProfileScreen(
    navController: NavController,
    user: User,
    editProfileViewModelFactory: EditProfileViewModelFactory
) {
    val viewModel = viewModel<EditProfileViewModel>(
        factory = editProfileViewModelFactory
    )

    val changed by viewModel.onComplete.collectAsState()
    val resetPass by viewModel.onResetComplete.collectAsState()

    EditProfileContent(
        user = user,
        onSaveClick = { newUser ->
            viewModel.updateUserDetails(
                userId = newUser.userId,
                newName = newUser.name,
                newEmail = newUser.email
            )
            if (changed) {
                navController.navigate("profile")
            }
        },
        onResetPassClick = { email ->
            viewModel.resetPassword(email)
        }
    )
}