package com.binlistapp.di.component

import android.app.Application
import com.binlistapp.di.module.*
import com.binlistapp.presentation.main.App
import dagger.BindsInstance
import dagger.Component
import ru.myproject.currencyconverter.di.scope.ApplicationScope

@Component(
    modules = [
        NetworkModule::class,
        ViewModelsModule::class,
        ViewModelFactoryModule::class,
        DispatchersModule::class,
        AppSubcomponents::class,
        DataBaseModule::class,
    ]
)

@ApplicationScope
interface AppComponent {

    fun inject(app: App)
    fun binCheckFragmentComponent(): BinCheckComponent.Factory

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): AppComponent
    }
}