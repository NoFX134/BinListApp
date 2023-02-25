package com.binlistapp.mapper

import com.binlistapp.data.model.dto.BinItemDto
import com.binlistapp.data.model.entities.BinItem
import javax.inject.Inject

class BinItemMapper @Inject constructor() {

    fun toEntity(binItemDto: BinItemDto): BinItem {
        return BinItem(
            binNumber = binItemDto.binNumber
        )
    }
}