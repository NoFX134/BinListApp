package com.binlistapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.binlistapp.di.factories.DaggerViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(daggerViewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

    companion object {

        @Provides
        fun provideDaggerViewModelFactory(
            viewModelsMap: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
        ): DaggerViewModelFactory {
            return DaggerViewModelFactory(viewModelsMap)
        }
    }
}