package com.binlistapp.mapper

import android.app.Application
import com.binlistapp.data.model.entities.CardInformation
import com.binlistapp.data.model.enums.DebitCredit
import com.binlistapp.data.model.network.CardInformationResponse
import com.example.binlistapp.R
import ru.myproject.currencyconverter.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class CardInformationMapper @Inject constructor(private val application: Application) {

    fun toEntity(cardInformationResponse: CardInformationResponse): CardInformation {
        return CardInformation(
            length = cardInformationResponse.numberResponse?.length.toString(),
            luhn = cardInformationResponse.numberResponse?.luhn,
            scheme = cardInformationResponse.scheme ?: "",
            type = when (cardInformationResponse.type){
                application.getString(R.string.debit) -> DebitCredit.Debit
                application.getString(R.string.credit) -> DebitCredit.Credit
                else -> DebitCredit.Undefined
            },
            brand = cardInformationResponse.brand ?: "",
            prepaid = cardInformationResponse.prepaid,
            emoji = cardInformationResponse.countryResponse?.emoji ?: "",
            country = cardInformationResponse.countryResponse?.name ?: "",
            latitude = cardInformationResponse.countryResponse?.latitude,
            longitude = cardInformationResponse.countryResponse?.longitude,
            bankCity = cardInformationResponse.bankResponse?.city ?: "",
            bankName = cardInformationResponse.bankResponse?.name ?: "",
            bankPhone = cardInformationResponse.bankResponse?.phone ?: "",
            bankUrl = cardInformationResponse.bankResponse?.url ?: "",
        )
    }
}