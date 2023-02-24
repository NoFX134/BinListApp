package com.binlistapp.data.remote

import com.binlistapp.data.model.network.CardInformationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi {

    @GET("/{bin}")
    suspend fun fetchCardInformation(@Path("bin") bin: String): CardInformationResponse
}