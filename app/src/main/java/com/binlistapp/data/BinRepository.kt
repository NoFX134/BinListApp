package com.binlistapp.data

import com.binlistapp.data.local.database.BinItemDao
import com.binlistapp.data.model.dto.BinItemDto
import com.binlistapp.data.model.entities.BinItem
import com.binlistapp.data.model.entities.CardInformation
import com.binlistapp.data.remote.BinApi
import com.binlistapp.mapper.BinItemMapper
import com.binlistapp.mapper.CardInformationMapper
import kotlinx.coroutines.flow.*
import ru.myproject.currencyconverter.di.scope.ApplicationScope
import javax.inject.Inject

@ApplicationScope
class BinRepository @Inject constructor(
    private val binApi: BinApi,
    private val binItemDao: BinItemDao,
    private val cardInformationMapper: CardInformationMapper,
    private val binItemMapper: BinItemMapper,
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

    fun fetchBinItemList(): Flow<List<BinItem>> {
        return binItemDao.getAll()
            .map { listBinItem -> listBinItem.map {binItemMapper.toEntity(it) } }
    }

    suspend fun insertBinItem(bin: String) {
        binItemDao.insert(BinItemDto(bin))
    }
}