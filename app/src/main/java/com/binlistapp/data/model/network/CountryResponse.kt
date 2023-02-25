package com.binlistapp.data.model.network

data class CountryResponse(
    val numeric: Short?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Double?,
    val longitude: Double?,
)