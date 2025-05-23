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
    val familyId by viewModel.callback.collectAsState()

    FinishSubSkillContent(
        subSkill = subSkill!!,
        familyMembers = familyMembers,
        onEndCLick = { imageUri, time, subSkillFinish ->
            if (familyId != null) {
//                viewModel.updateUserPoints(
//                    familyId = familyId!!,
//                    pointsToAdd = subSkill.points,
//                )
                viewModel.createTask(imageUri = imageUri, pointsToAdd = subSkill.points, name = subSkill.name)
                viewModel.updateSubSkillInRoom(subSkillFinish)
                navController.navigate("skills")
            }
        },
        onBackClick = {
            navController.navigate("skills")
        }
    )
}