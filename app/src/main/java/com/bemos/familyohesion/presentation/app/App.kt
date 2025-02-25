package com.bemos.familyohesion.presentation.app

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import com.bemos.familyohesion.domain.models.SubSkill
import com.bemos.familyohesion.presentation.di.app_component.AppComponent
import com.bemos.familyohesion.presentation.di.app_component.DaggerAppComponent
import com.google.firebase.Firebase
import com.google.firebase.initialize

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        var listOfSubSkills = mutableStateListOf<SubSkill>()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory()
            .create(this)
        Firebase.initialize(this)
    }
}