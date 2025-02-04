package com.bemos.familyohesion.presentation.features.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.profile.vm.ProfileViewModel
import com.bemos.familyohesion.presentation.features.profile.vm.factory.ProfileViewModelFactory

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModelFactory: ProfileViewModelFactory
) {
    val viewModel = viewModel<ProfileViewModel>(
        factory = profileViewModelFactory
    )

    val onComplete by viewModel.onComplete.collectAsState()
    val onFailure by viewModel.onFailure.collectAsState()
    val onResult by viewModel.onResult.collectAsState()

    ProfileContent(
        user = onComplete,
        familyMembers = onResult
    )
}