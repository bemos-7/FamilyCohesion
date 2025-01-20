package com.bemos.familyohesion.presentation.di.app_component

import android.content.Context
import com.bemos.familyohesion.data.di.module.DataModule
import com.bemos.familyohesion.presentation.di.module.AppModule
import com.bemos.familyohesion.presentation.main_activity.MainActivity
import com.bemos.familyohesion.presentation.app.App
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        DataModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): AppComponent
    }
}
val Context.appComponent : AppComponent
    get() {
    return if (this is App) {
        appComponent
    } else {
        applicationContext.appComponent
    }
}