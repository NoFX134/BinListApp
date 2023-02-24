package com.binlistapp.mapper

import com.binlistapp.data.model.entities.CardInformation
import com.binlistapp.data.model.network.CardInformationResponse
import ru.myproject.currencyconverter.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class CardInformationMapper @Inject constructor() {

    fun toEntity(cardInformationResponse: CardInformationResponse): CardInformation {
        return CardInformation(
            length = cardInformationResponse.numberResponse?.length,
            luhn = cardInformationResponse.numberResponse?.luhh,
            scheme = cardInformationResponse.scheme ?: "",
            type = cardInformationResponse.type ?: "",
            brand = cardInformationResponse.brand ?: "",
            prepaid = cardInformationResponse.prepaid,
            emoji = cardInformationResponse.countryResponse?.emoji ?: "",
            country = cardInformationResponse.countryResponse?.name ?: "",
            latitude = cardInformationResponse.countryResponse?.latitude,
            longitude = cardInformationResponse.countryResponse?.longitude,
            bankCity = cardInformationResponse.bankResponse?.city ?: "",
            bankName = cardInformationResponse.bankResponse?.name ?: "",
            bankPhone = cardInformationResponse.bankResponse?.phone ?: "",
            bankUrl = cardInformationResponse.bankResponse?.name ?: "",
        )
    }
}