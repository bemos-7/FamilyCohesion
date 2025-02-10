package com.bemos.familyohesion.presentation.features.add_family_member

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.add_family_member.vm.AddFamilyMemberViewModel
import com.bemos.familyohesion.presentation.features.add_family_member.vm.factory.AddFamilyMemberViewModelFactory

@Composable
fun AddFamilyMemberScreen(
    navController: NavController,
    addFamilyMemberViewModelFactory: AddFamilyMemberViewModelFactory,
    familyId: String?
) {
    val context = LocalContext.current

    val viewModel = viewModel<AddFamilyMemberViewModel>(
        factory = addFamilyMemberViewModelFactory
    )

    val onSuccess by viewModel.onSuccess.collectAsState()
    val onFailure by viewModel.onFailure.collectAsState()

    LaunchedEffect(Unit) {
        Toast.makeText(context, familyId!!, Toast.LENGTH_SHORT).show()
    }

    if (onSuccess) {
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
        navController.navigate("profile")
    }

    AddFamilyMemberContent(
        onRegisterClick = { userAuth ->
            viewModel.signUpAndJoinFamily(
                userAuth = userAuth,
                familyId = familyId!!
            )
        }
    )
}