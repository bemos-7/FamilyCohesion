package com.bemos.familyohesion.presentation.features.finish_subSkill

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.FinishSubSkillViewModel
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.factory.FinishSubSkillViewModelFactory

@Composable
fun FinishSubSkillScreen(
    navController: NavController,
    subSkillName: String?,
    finishSubSkillViewModelFactory: FinishSubSkillViewModelFactory
) {

    val viewModel = viewModel<FinishSubSkillViewModel>(
        factory = finishSubSkillViewModelFactory
    )

    val familyMembers by viewModel.onResult.collectAsState()

    FinishSubSkillContent(
        subSkillName = subSkillName!!,
        familyMembers = familyMembers
    )
}