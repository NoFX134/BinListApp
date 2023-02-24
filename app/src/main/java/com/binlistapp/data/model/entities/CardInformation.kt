package com.binlistapp.data.model.entities

data class CardInformation(
    val length: Short?,
    val luhn: Boolean?,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean?,
    val emoji: String,
    val country: String,
    val longitude: Short?,
    val latitude: Short?,
    val bankName: String,
    val bankCity: String,
    val bankUrl: String,
    val bankPhone: String,
)