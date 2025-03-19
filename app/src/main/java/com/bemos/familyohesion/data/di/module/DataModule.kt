package com.bemos.familyohesion.data.di.module

import com.bemos.familyohesion.data.remote.firebase.repository.impl.FirebaseAuthImpl
import com.bemos.familyohesion.data.remote.firebase.repository.impl.FirebaseFirestoreImpl
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository
import com.bemos.familyohesion.domain.use_cases.DeleteUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserIdUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.SendPasswordResetEmailUseCase
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpAndJoinFamilyUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserDetailsUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @Provides
    fun provideFirebase(): Firebase {
        return Firebase
    }

    @Provides
    fun provideFirebaseAuth(
        firebase: Firebase
    ): FirebaseAuth {
        return firebase.auth
    }

    @Provides
    fun provideFirebaseFirestore(
        firebase: Firebase
    ): FirebaseFirestore {
        return firebase.firestore
    }

    @Provides
    fun provideFirebaseAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): FirebaseAuthRepository {
        return FirebaseAuthImpl(
            firebaseAuth = firebaseAuth,
            firebaseFirestore = firestore
        )
    }

    @Provides
    fun provideFirebaseFirestoreRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): FirebaseFirestoreRepository {
        return FirebaseFirestoreImpl(
            firestore = firestore,
            auth = auth
        )
    }

    @Provides
    fun provideSignUpUseCase(
        firebaseAuthRepository: FirebaseAuthRepository
    ): SignUpUseCase {
        return SignUpUseCase(
            repository = firebaseAuthRepository
        )
    }

    @Provides
    fun provideSignInUseCase(
        firebaseAuthRepository: FirebaseAuthRepository
    ): SignInUseCase {
        return SignInUseCase(
            repository = firebaseAuthRepository
        )
    }

    @Provides
    fun provideGetSkillsUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetSkillsUseCase {
        return GetSkillsUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun getUserDataUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetUserDataUseCase {
        return GetUserDataUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun getFamilyMembersUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetFamilyMembersUseCase {
        return GetFamilyMembersUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun signUpAndJoinFamilyUseCase(
        firebaseAuthRepository: FirebaseAuthRepository
    ): SignUpAndJoinFamilyUseCase {
        return SignUpAndJoinFamilyUseCase(
            repository = firebaseAuthRepository
        )
    }

    @Provides
    fun getCategoriesUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetCategoriesUseCase {
        return GetCategoriesUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun getSubSkillsUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetSubSkillsUseCase {
        return GetSubSkillsUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun getCurrentUserIdUseCase(
        firebaseAuthRepository: FirebaseAuthRepository
    ): GetCurrentUserIdUseCase {
        return GetCurrentUserIdUseCase(
            repository = firebaseAuthRepository
        )
    }

    @Provides
    fun deleteUserUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): DeleteUserUseCase {
        return DeleteUserUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun updateUserDetailsUserCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): UpdateUserDetailsUseCase {
        return UpdateUserDetailsUseCase(
            repository = firebaseFirestoreRepository
        )
    }

    @Provides
    fun provideSendPasswordResetEmailUseCase(
        firebaseAuthRepository: FirebaseAuthRepository
    ): SendPasswordResetEmailUseCase {
        return SendPasswordResetEmailUseCase(
            firebaseAuthRepository = firebaseAuthRepository
        )
    }
}