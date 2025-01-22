package com.bemos.familyohesion.presentation.di.module

import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
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

}