package com.binlistapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binlistapp.data.model.dto.BinItemDto

@Database(
    entities = [BinItemDto::class],
    version = 1,
    exportSchema = false
)
abstract class BinItemDatabase : RoomDatabase() {

    abstract fun getBinItemDao(): BinItemDao

    companion object {

        const val DATABASE_NAME = "bin_item_database"
    }
}