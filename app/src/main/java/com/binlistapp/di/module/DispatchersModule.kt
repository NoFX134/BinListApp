package com.binlistapp.di.module

import com.binlistapp.di.qualifiers.dispatchers.DispatcherDefault
import com.binlistapp.di.qualifiers.dispatchers.DispatcherIO
import com.binlistapp.di.qualifiers.dispatchers.DispatcherMain
import com.binlistapp.di.qualifiers.dispatchers.DispatcherUnconfined
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
object DispatchersModule {

    @Provides
    @DispatcherMain
    fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DispatcherIO
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @DispatcherDefault
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @DispatcherUnconfined
    fun provideDispatcherUnconfined(): CoroutineDispatcher = Dispatchers.Unconfined
}