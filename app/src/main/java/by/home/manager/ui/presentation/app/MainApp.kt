package by.home.manager.ui.presentation.app

import android.app.Application
import android.content.Context
import by.home.manager.ui.data.di.AppComponent
import by.home.manager.ui.data.di.DaggerAppComponent

class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().context(this).build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }
