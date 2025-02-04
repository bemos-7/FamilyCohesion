package com.bemos.familyohesion.presentation.di.module

import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import com.bemos.familyohesion.presentation.features.profile.vm.factory.ProfileViewModelFactory
import com.bemos.familyohesion.presentation.features.select_skill.vm.SelectSkillViewModel
import com.bemos.familyohesion.presentation.features.select_skill.vm.factory.SelectSkillViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_up.vm.factory.SignUpViewModelFactory
import dagger.Module
import dagger.Provides
import kotlin.math.sign

@Module
class AppModule {

    @Provides
    fun provideSignUpViewModelFactory(
        signUpUseCase: SignUpUseCase
    ): SignUpViewModelFactory {
        return SignUpViewModelFactory(
            signUpUseCase = signUpUseCase
        )
    }

    @Provides
    fun provideSignInViewModelFactory(
        signInUseCase: SignInUseCase
    ): SignInViewModelFactory {
        return SignInViewModelFactory(
            signInUseCase = signInUseCase
        )
    }

    @Provides
    fun provideSelectSkillViewModelFactory(
        getSkillsUseCase: GetSkillsUseCase
    ): SelectSkillViewModelFactory {
        return SelectSkillViewModelFactory(
            getSkillsUseCase = getSkillsUseCase
        )
    }

    @Provides
    fun provideProfileViewModelFactory(
        getUserDataUseCase: GetUserDataUseCase,
        getFamilyMembersUseCase: GetFamilyMembersUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getUserDataUseCase = getUserDataUseCase,
            getFamilyMembersUseCase = getFamilyMembersUseCase
        )
    }

}