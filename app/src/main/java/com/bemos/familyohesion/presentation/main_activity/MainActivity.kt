package com.bemos.familyohesion.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.di.app_component.appComponent
import com.bemos.familyohesion.presentation.features.add_family_member.AddFamilyMemberScreen
import com.bemos.familyohesion.presentation.features.add_family_member.vm.factory.AddFamilyMemberViewModelFactory
import com.bemos.familyohesion.presentation.features.finish_subSkill.FinishSubSkillScreen
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.factory.FinishSubSkillViewModelFactory
import com.bemos.familyohesion.presentation.features.profile.ProfileScreen
import com.bemos.familyohesion.presentation.features.profile.vm.factory.ProfileViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_in.SignInScreen
import com.bemos.familyohesion.presentation.features.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_up.SignUpScreen
import com.bemos.familyohesion.presentation.features.sign_up.vm.factory.SignUpViewModelFactory
import com.bemos.familyohesion.presentation.features.skills.SkillsScreen
import com.bemos.familyohesion.presentation.features.skills.vm.factory.SkillsViewModelFactory
import com.bemos.familyohesion.presentation.main_activity.util.bottomNavItems
import com.bemos.familyohesion.presentation.main_activity.vm.MainViewModel
import com.bemos.familyohesion.presentation.main_activity.vm.factory.MainViewModelFactory
import com.bemos.familyohesion.core.ui.theme.FamilyСohesionTheme
import com.bemos.familyohesion.domain.models.User
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.presentation.features.add_family_member.navigation.AddFamilyMemberScreen
import com.bemos.familyohesion.presentation.features.edit_profile.EditProfileScreen
import com.bemos.familyohesion.presentation.features.edit_profile.vm.factory.EditProfileViewModelFactory
import com.bemos.familyohesion.presentation.features.finish_subSkill.navigation.FinishSubSkillScreen
import com.bemos.familyohesion.presentation.features.rating.RatingScreen
import com.bemos.familyohesion.presentation.features.rating.vm.RatingViewModel
import com.bemos.familyohesion.presentation.features.rating.vm.factory.RatingViewModelFactory
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory

    @Inject
    lateinit var addFamilyMemberViewModelFactory: AddFamilyMemberViewModelFactory

    @Inject
    lateinit var skillsViewModelFactory: SkillsViewModelFactory

    @Inject
    lateinit var finishSubSkillViewModelFactory: FinishSubSkillViewModelFactory

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory

    @Inject
    lateinit var ratingViewModelFactory: RatingViewModelFactory

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamilyСohesionTheme {
                val mainViewModel = viewModel<MainViewModel>(
                    factory = mainViewModelFactory
                )
                val currentUser by mainViewModel.onResult.collectAsState()
                val startNav = if (currentUser.isNotEmpty()) "skills" else "signIn"

                var isBottomNavVisible by remember {
                    mutableStateOf(false)
                }
                var selectedItemIndex by rememberSaveable {
                    mutableStateOf(0)
                }
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        if (isBottomNavVisible) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                            ) {
                                bottomNavItems.forEachIndexed { index, bottomNavItem ->
                                    NavigationBarItem(
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navController.navigate(bottomNavItem.route)
                                        },
                                        icon = {
                                            BadgedBox(
                                                badge = {

                                                },
                                            ) {
                                                Icon(
                                                    painter = painterResource(bottomNavItem.icon),
                                                    contentDescription = bottomNavItem.title,
                                                )
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = bottomNavItem.title
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = MaterialTheme.colorScheme.primary.copy(0.2f)
                                        )
                                    )
                                }
                            }
                        }
                    },
                    contentWindowInsets = WindowInsets(0, 0, 0, 0)
                ) {
                    NavHost(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        startDestination = startNav
                    ) {
                        composable(
                            route = "signUp"
                        ) {
                            SignUpScreen(
                                navController = navController,
                                signUpViewModelFactory = signUpViewModelFactory
                            )
                            isBottomNavVisible = false
                        }
                        composable(
                            route = "signIn"
                        ) {
                            SignInScreen(
                                navController = navController,
                                signInViewModelFactory = signInViewModelFactory
                            )
                            isBottomNavVisible = false
                        }
                        composable(
                            route = "profile"
                        ) {
                            ProfileScreen(
                                navController = navController,
                                profileViewModelFactory = profileViewModelFactory
                            )
                            isBottomNavVisible = true
                        }
                        composable(
                            route = "editProfile/{user}",
                            arguments = listOf(
                                navArgument("user") {
                                    type = NavType.StringType
                                }
                            )
                        ) { navBackStackEntry ->
                            val userJson = navBackStackEntry.arguments?.getString("user")
                            val user = Json.decodeFromString<User>(userJson.toString())
                            EditProfileScreen(
                                navController = navController,
                                user = user,
                                editProfileViewModelFactory = editProfileViewModelFactory
                            )
                            isBottomNavVisible = true
                        }
//                        composable(
//                            route = "addFamilyMember/{familyId}",
//                            arguments = listOf(
//                                navArgument("familyId") {
//                                    type = NavType.StringType
//                                }
//                            )
//                        ) { navBackStackEntry ->
//                            AddFamilyMemberScreen(
//                                navController = navController,
//                                addFamilyMemberViewModelFactory = addFamilyMemberViewModelFactory,
//                                familyId = navBackStackEntry.arguments?.getString("faimlyId")
//                            )
//                        }
                        composable<AddFamilyMemberScreen> { navBackStackEntry ->
                            val familyMember = navBackStackEntry.toRoute<AddFamilyMemberScreen>()
                            AddFamilyMemberScreen(
                                navController = navController,
                                addFamilyMemberViewModelFactory = addFamilyMemberViewModelFactory,
                                familyId = familyMember.familyId
                            )
                            isBottomNavVisible = true
                        }
                        composable(
                            route = "skills"
                        ) {
                            SkillsScreen(
                                navController = navController,
                                skillsViewModelFactory = skillsViewModelFactory
                            )
                            isBottomNavVisible = true
                        }
                        composable(
                            route = "finishSubSkill/{subSkill}",
                            arguments = listOf(
                                navArgument("subSkill") {
                                    type = NavType.StringType
                                }
                            )
                        ) { navBackStackEntry ->
                            val subSkillJson = navBackStackEntry.arguments?.getString("subSkill")
                            val subSkill = Json.decodeFromString<SubSkill>(subSkillJson!!)
                            FinishSubSkillScreen(
                                navController = navController,
                                subSkill = subSkill,
                                finishSubSkillViewModelFactory = finishSubSkillViewModelFactory
                            )
                            isBottomNavVisible = true
                        }

                        composable(
                            route = "rating"
                        ) {
                            RatingScreen(
                                navController,
                                ratingViewModelFactory = ratingViewModelFactory
                            )
                        }
                    }
                }

            }
        }
    }
}

