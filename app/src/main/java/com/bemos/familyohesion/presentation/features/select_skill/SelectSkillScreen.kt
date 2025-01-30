package com.bemos.familyohesion.presentation.features.select_skill

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.select_skill.vm.SelectSkillViewModel
import com.bemos.familyohesion.presentation.features.select_skill.vm.factory.SelectSkillViewModelFactory

@Composable
fun SelectSkillScreen(
    navController: NavController,
    selectSkillViewModelFactory: SelectSkillViewModelFactory
) {
    val viewModel = viewModel<SelectSkillViewModel>(
        factory = selectSkillViewModelFactory
    )

    val onComplete by viewModel.onComplete.collectAsState()

    viewModel.getSkills()

    SelectSkillContent(
        skills = onComplete
    )
}