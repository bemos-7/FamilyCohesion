package com.bemos.familyohesion.presentation.features.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.profile.vm.ProfileViewModel
import com.bemos.familyohesion.presentation.features.profile.vm.factory.ProfileViewModelFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModelFactory: ProfileViewModelFactory
) {
    val viewModel = viewModel<ProfileViewModel>(
        factory = profileViewModelFactory
    )
    val onDeleteComplete by viewModel.onDeleteComplete.collectAsState()
    val onComplete by viewModel.onComplete.collectAsState()
    val onFailure by viewModel.onFailure.collectAsState()
    val onResult by viewModel.onResult.collectAsState()

    ProfileContent(
        user = onComplete,
        familyMembers = onResult,
        onFamilyMemberAdd = {
            navController.navigate("addFamilyMember/$it")
        },
        onDeleteUserClick = { userId ->
            viewModel.deleteUser(userId)
            viewModel.getUserData()
        },
        onEditProfileClick = { user ->
            val userJson = Json.encodeToString(user)
            navController.navigate("editProfile/$userJson")
        }
    )
}