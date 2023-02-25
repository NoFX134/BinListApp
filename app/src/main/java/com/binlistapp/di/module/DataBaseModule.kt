package com.binlistapp.di.module

import android.app.Application
import androidx.room.Room
import com.binlistapp.data.local.database.BinItemDao
import com.binlistapp.data.local.database.BinItemDatabase
import dagger.Module
import dagger.Provides
import ru.myproject.currencyconverter.di.scope.ApplicationScope

@Module
class DataBaseModule {

    @Provides
    @ApplicationScope
    fun provideBinItemDatabase(
        application: Application,
    ): BinItemDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            BinItemDatabase::class.java,
            BinItemDatabase.DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideBinItemDao(
        currencyDatabase: BinItemDatabase
    ): BinItemDao {
        return currencyDatabase.getBinItemDao()
    }
}