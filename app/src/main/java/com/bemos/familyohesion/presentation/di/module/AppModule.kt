package com.bemos.familyohesion.presentation.di.module

import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpAndJoinFamilyUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import com.bemos.familyohesion.presentation.features.add_family_member.vm.factory.AddFamilyMemberViewModelFactory
import com.bemos.familyohesion.presentation.features.finish_subSkill.vm.factory.FinishSubSkillViewModelFactory
import com.bemos.familyohesion.presentation.features.profile.vm.factory.ProfileViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_in.vm.factory.SignInViewModelFactory
import com.bemos.familyohesion.presentation.features.sign_up.vm.factory.SignUpViewModelFactory
import com.bemos.familyohesion.presentation.features.skills.vm.factory.SkillsViewModelFactory
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
    fun provideProfileViewModelFactory(
        getUserDataUseCase: GetUserDataUseCase,
        getFamilyMembersUseCase: GetFamilyMembersUseCase
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(
            getUserDataUseCase = getUserDataUseCase,
            getFamilyMembersUseCase = getFamilyMembersUseCase
        )
    }

    @Provides
    fun provideAddFamilyMemberViewModelFactory(
        signUpAndJoinFamilyUseCase: SignUpAndJoinFamilyUseCase
    ): AddFamilyMemberViewModelFactory {
        return AddFamilyMemberViewModelFactory(
            signUpAndJoinFamilyUseCase = signUpAndJoinFamilyUseCase
        )
    }

    @Provides
    fun provideSkillsViewModelFactory(
        getCategoriesUseCase: GetCategoriesUseCase,
        getSkillsUseCase: GetSkillsUseCase,
        getSubSkillsUseCase: GetSubSkillsUseCase
    ): SkillsViewModelFactory {
        return SkillsViewModelFactory(
            getCategoriesUseCase = getCategoriesUseCase,
            getSkillsUseCase = getSkillsUseCase,
            getSubSkillsUseCase = getSubSkillsUseCase
        )
    }

    @Provides
    fun provideFinishSubSkillViewModelFactory(
        getUserDataUseCase: GetUserDataUseCase,
        getFamilyMembersUseCase: GetFamilyMembersUseCase
    ): FinishSubSkillViewModelFactory {
        return FinishSubSkillViewModelFactory(
            getUserDataUseCase = getUserDataUseCase,
            getFamilyMembersUseCase = getFamilyMembersUseCase
        )
    }

}