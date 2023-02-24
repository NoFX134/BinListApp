package com.binlistapp.di.module

import androidx.lifecycle.ViewModel
import com.binlistapp.di.mapKeys.ViewModelKey
import com.binlistapp.presentation.viewModels.BinCheckViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(BinCheckViewModel::class)
    fun bindMainViewModel(instance: BinCheckViewModel): ViewModel
}