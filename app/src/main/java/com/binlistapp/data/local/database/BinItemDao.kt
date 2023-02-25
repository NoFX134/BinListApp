package com.binlistapp.data.local.database

import androidx.room.*
import com.binlistapp.data.model.dto.BinItemDto
import kotlinx.coroutines.flow.Flow

@Dao
interface BinItemDao {

    @Query("SELECT * FROM table_binItem")
    fun getAll(): Flow<List<BinItemDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(binItem: BinItemDto)

    @Delete
    suspend fun delete(binItem: BinItemDto)
}