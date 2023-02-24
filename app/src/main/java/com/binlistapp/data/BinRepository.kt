package com.binlistapp.data

import com.binlistapp.data.model.entities.CardInformation
import com.binlistapp.data.remote.BinApi
import com.binlistapp.mapper.CardInformationMapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.myproject.currencyconverter.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class BinRepository @Inject constructor(
    private val binApi: BinApi,
    private val cardInformationMapper: CardInformationMapper,
) {

    private val cardInformationMutableStateFlow = MutableStateFlow<CardInformation?>(null)
    val cardInformationStateFlow = cardInformationMutableStateFlow.asStateFlow()
    private val errorMutableSharedFlow = MutableSharedFlow<String>()
    val errorSharedFlow = errorMutableSharedFlow.asSharedFlow()
    private val loadingMutableStateFlow = MutableStateFlow(false)
    val loadingStateFlow = loadingMutableStateFlow.asStateFlow()

    suspend fun fetchCardInformation(bin: String) {
        loadingMutableStateFlow.value = true
        try {
            val response = binApi.fetchCardInformation(bin)
            cardInformationMutableStateFlow.value = cardInformationMapper.toEntity(response)
        } catch (throwable: Throwable) {
            errorMutableSharedFlow.emit(throwable.toString())
        } finally {
            loadingMutableStateFlow.value = false
        }
    }
}