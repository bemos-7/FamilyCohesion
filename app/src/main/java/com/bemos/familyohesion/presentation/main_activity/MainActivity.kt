package com.bemos.familyohesion.presentation.main_activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bemos.familyohesion.presentation.di.app_component.appComponent
import com.bemos.familyohesion.ui.theme.FamilyСohesionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FamilyСohesionTheme {

            }
        }
    }
}