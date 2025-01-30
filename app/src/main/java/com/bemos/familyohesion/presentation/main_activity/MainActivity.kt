package com.bemos.familyohesion.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bemos.familyohesion.presentation.di.app_component.appComponent
import com.bemos.familyohesion.presentation.features.select_skill.SelectSkillScreen
import com.bemos.familyohesion.presentation.features.select_skill.vm.factory.SelectSkillViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_in.SignInScreen
import com.bemos.familyohesion.presentation.features.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_up.SignUpScreen
import com.bemos.familyohesion.presentation.features.sign_up.vm.factory.SignUpViewModelFactory
import com.bemos.familyohesion.ui.theme.FamilyСohesionTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    @Inject
    lateinit var selectSkillViewModelFactory: SelectSkillViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamilyСohesionTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "selectSkill") {
                    composable(
                        route = "signUp"
                    ) {
                        SignUpScreen(
                            navController = navController,
                            signUpViewModelFactory = signUpViewModelFactory
                        )
                    }
                    composable(
                        route = "signIn"
                    ) {
                        SignInScreen(
                            signInViewModelFactory = signInViewModelFactory
                        )
                    }
                    composable(
                        route = "selectSkill"
                    ) {
                        SelectSkillScreen(
                            navController = navController,
                            selectSkillViewModelFactory = selectSkillViewModelFactory
                        )
                    }
                }
            }
        }
    }
}