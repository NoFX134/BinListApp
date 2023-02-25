package com.binlistapp.data.model.network

import com.google.gson.annotations.SerializedName

data class CardInformationResponse(
    @field:SerializedName("number")
    val numberResponse: NumberResponse?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    @field:SerializedName("country")
    val countryResponse: CountryResponse?,
    @field:SerializedName("bank")
    val bankResponse: BankResponse?,
)