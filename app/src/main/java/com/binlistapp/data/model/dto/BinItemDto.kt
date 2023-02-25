package com.binlistapp.data.model.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_binItem")
data class BinItemDto(
    @PrimaryKey @ColumnInfo(name = "binNumber")val binNumber: String
)