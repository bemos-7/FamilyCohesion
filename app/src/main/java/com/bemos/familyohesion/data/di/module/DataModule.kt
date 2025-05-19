package com.bemos.familyohesion.data.di.module

import android.content.Context
import androidx.room.Room
import com.bemos.familyohesion.data.local.dao.CategoryDao
import com.bemos.familyohesion.data.local.dao.SkillDao
import com.bemos.familyohesion.data.local.dao.SubSkillDao
import com.bemos.familyohesion.data.local.dao.impl.DaoRepositoryImpl
import com.bemos.familyohesion.data.local.db.Database
import com.bemos.familyohesion.data.remote.firebase.repository.impl.FirebaseAuthImpl
import com.bemos.familyohesion.data.remote.firebase.repository.impl.FirebaseFirestoreImpl
import com.bemos.familyohesion.data.remote.firebase.repository.impl.TaskImpl
import com.bemos.familyohesion.domain.repositories.DaoRepository
import com.bemos.familyohesion.domain.repositories.FirebaseAuthRepository
import com.bemos.familyohesion.domain.repositories.FirebaseFirestoreRepository
import com.bemos.familyohesion.domain.repositories.TaskRepository
import com.bemos.familyohesion.domain.use_cases.CreateTaskUseCase
import com.bemos.familyohesion.domain.use_cases.DeleteUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetAllSubSkillsRoomUseCase
import com.bemos.familyohesion.domain.use_cases.GetCategoriesUseCase
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserIdUseCase
import com.bemos.familyohesion.domain.use_cases.GetCurrentUserPointsUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyIdForCurrentUserUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyMembersUseCase
import com.bemos.familyohesion.domain.use_cases.GetFamilyRatingsUseCase
import com.bemos.familyohesion.domain.use_cases.GetPendingTasksUseCase
import com.bemos.familyohesion.domain.use_cases.GetSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetSubSkillsUseCase
import com.bemos.familyohesion.domain.use_cases.GetUserDataUseCase
import com.bemos.familyohesion.domain.use_cases.InsertSubSkillRoomUseCase
import com.bemos.familyohesion.domain.use_cases.SendPasswordResetEmailUseCase
import com.bemos.familyohesion.domain.use_cases.SignInUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpAndJoinFamilyUseCase
import com.bemos.familyohesion.domain.use_cases.SignUpUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateSubSkillRoomUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserDetailsUseCase
import com.bemos.familyohesion.domain.use_cases.UpdateUserPointsUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
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
    fun provideFirebaseStorage(
        firebase: Firebase
    ): FirebaseStorage {
        return firebase.storage
    }

    @Provides
    fun provideDatabase(
        context: Context
    ) : Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "db"
        ).build()
    }

    @Provides
    fun provideCategoryDao(
        database: Database
    ): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideSkillDao(
        database: Database
    ): SkillDao {
        return database.skillDao()
    }

    @Provides
    fun provideSubSkillDao(
        database: Database
    ): SubSkillDao {
        return database.subSkillDao()
    }

    @Provides
    fun provideDaoRepositoryImpl(
        categoryDao: CategoryDao,
        skillDao: SkillDao,
        subSkillDao: SubSkillDao
    ): DaoRepository {
        return DaoRepositoryImpl(
            categoryDao = categoryDao,
            skillDao = skillDao,
            subSkillDao = subSkillDao
        )
    }

    @Provides
    fun provideTaskRepository(
       storage: FirebaseStorage,
       firestore: FirebaseFirestore,
       firebaseAuth: FirebaseAuth
    ): TaskRepository {
        return TaskImpl(
            firestore = firestore,
            firebaseStorage = storage,
            firebaseAuth = firebaseAuth
        )
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

    @Provides
    fun provideGetAllSubSkillsRoomUseCase(
        daoRepository: DaoRepository
    ): GetAllSubSkillsRoomUseCase {
        return GetAllSubSkillsRoomUseCase(
            daoRepository
        )
    }

    @Provides
    fun provideUpdateSubSkillsRoomUseCase(
        daoRepository: DaoRepository
    ): UpdateSubSkillRoomUseCase {
        return UpdateSubSkillRoomUseCase(
            daoRepository
        )
    }

    @Provides
    fun provideInsertSubSkillRoomUseCase(
        daoRepository: DaoRepository
    ): InsertSubSkillRoomUseCase {
        return InsertSubSkillRoomUseCase(
            daoRepository
        )
    }

    @Provides
    fun provideUpdateUserPointsUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): UpdateUserPointsUseCase {
        return UpdateUserPointsUseCase(
            firebaseFirestoreRepository
        )
    }

    @Provides
    fun provideGetFamilyIdCurrentUser(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetFamilyIdForCurrentUserUseCase {
        return GetFamilyIdForCurrentUserUseCase(
            firebaseFirestoreRepository
        )
    }

    @Provides
    fun provideGetCurrentUserPointsUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetCurrentUserPointsUseCase {
        return GetCurrentUserPointsUseCase(
            firebaseFirestoreRepository
        )
    }

    @Provides
    fun provideGetFamilyRatingsUseCase(
        firebaseFirestoreRepository: FirebaseFirestoreRepository
    ): GetFamilyRatingsUseCase {
        return GetFamilyRatingsUseCase(
            firebaseFirestoreRepository
        )
    }

    @Provides
    fun provideCreateTaskUse(
        taskRepository: TaskRepository
    ): CreateTaskUseCase {
        return CreateTaskUseCase(
            taskRepository
        )
    }

    @Provides
    fun provideGetPendingTasksUseCase(
        taskRepository: TaskRepository
    ): GetPendingTasksUseCase {
        return GetPendingTasksUseCase(
            taskRepository = taskRepository
        )
    }
}