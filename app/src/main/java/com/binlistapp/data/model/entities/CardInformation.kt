package com.binlistapp.data.model.entities

import com.binlistapp.data.model.enums.DebitCredit

data class CardInformation(
    val length: String,
    val luhn: Boolean?,
    val scheme: String,
    val type: DebitCredit,
    val brand: String,
    val prepaid: Boolean?,
    val emoji: String,
    val country: String,
    val longitude: Double?,
    val latitude: Double?,
    val bankName: String,
    val bankCity: String,    val bankUrl: String,
    val bankPhone: String,
)