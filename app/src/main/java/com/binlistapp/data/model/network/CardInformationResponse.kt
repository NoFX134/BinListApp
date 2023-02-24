package com.binlistapp.data.model.network

data class CardInformationResponse (
    val numberResponse: NumberResponse?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val countryResponse: CountryResponse?,
    val bankResponse: BankResponse?,
    )