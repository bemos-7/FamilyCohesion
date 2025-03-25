package com.bemos.familyohesion.presentation.features.skills

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bemos.familyohesion.presentation.app.App
import com.bemos.familyohesion.presentation.features.skills.vm.SkillsViewModel
import com.bemos.familyohesion.presentation.features.skills.vm.factory.SkillsViewModelFactory
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun SkillsScreen(
    navController: NavController,
    skillsViewModelFactory: SkillsViewModelFactory
) {
    val viewModel = viewModel<SkillsViewModel>(
        factory = skillsViewModelFactory
    )

    val categories by viewModel.onCategoryComplete.collectAsState()
    val skills by viewModel.onSkillsComplete.collectAsState()
    val subSkills by viewModel.onSubSkillsComplete.collectAsState()
    val subSkillsFromRoom by viewModel.subSkillRoom.collectAsState()
    val points by viewModel.pointsCallback.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCategory()
        viewModel.getSkills("category1")
        viewModel.getAllSubSkillsFromRoom()
    }

    Log.d("firebaseTest", categories.toString())
    Log.d("AppCheck", App.endingSubSkills.toString())

    SkillsContent(
        points = points ?: 0,
        categories = categories,
        skills = skills,
        subSkills = subSkills,
        localSubSkills = subSkillsFromRoom,
        onSkillClick = { skillId ->
            viewModel.getSubSkills(
                skillId
            )
        },
        onFinishSubSkill = { subSkill ->
            val subSkillJson = Json.encodeToString(subSkill)
            navController.navigate("finishSubSkill/${subSkillJson}")
        },
        selectedCategory = { skill ->
            viewModel.getSkills(skill.id)
        },
        onSubSkillClick = { subSkill ->
            viewModel.insertSubSkillInRoom(subSkill)
            viewModel.getAllSubSkillsFromRoom()
        }
    )
}