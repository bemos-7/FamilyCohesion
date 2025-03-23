package com.bemos.familyohesion.presentation.features.finish_subSkill

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.app.App
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.FinishSubSkillViewModel
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.factory.FinishSubSkillViewModelFactory

@Composable
fun FinishSubSkillScreen(
    navController: NavController,
    subSkill: SubSkill?,
    finishSubSkillViewModelFactory: FinishSubSkillViewModelFactory
) {
    val viewModel = viewModel<FinishSubSkillViewModel>(
        factory = finishSubSkillViewModelFactory
    )

    val familyMembers by viewModel.onResult.collectAsState()

    FinishSubSkillContent(
        subSkill = subSkill!!,
        familyMembers = familyMembers,
        onEndCLick = { subSkillFinish, time ->
            viewModel.updateSubSkillInRoom(subSkillFinish)
            navController.navigate("skills")
        },
        onBackClick = {
            navController.navigate("skills")
        }
    )
}