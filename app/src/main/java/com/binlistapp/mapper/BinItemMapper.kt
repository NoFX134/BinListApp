package com.binlistapp.mapper

import com.binlistapp.data.model.dto.BinItemDto
import com.binlistapp.data.model.entities.BinItem
import ru.myproject.currencyconverter.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class BinItemMapper @Inject constructor() {

    fun toEntity(binItemDto: BinItemDto): BinItem {
        return BinItem(
            binNumber = binItemDto.binNumber
        )
    }

    fun toDto(binItem: BinItem): BinItemDto {
        return BinItemDto(
            binNumber = binItem.binNumber
        )
    }
}