package com.binlistapp.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binlistapp.data.BinRepository
import com.binlistapp.data.model.entities.BinItem
import com.binlistapp.di.qualifiers.dispatchers.DispatcherIO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BinCheckViewModel @Inject constructor(
    private val binRepository: BinRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : ViewModel() {

    init {
        fetchBinItemList()
    }

    val loadingStateFlow = binRepository.loadingStateFlow
    val errorSharedFlow = binRepository.errorSharedFlow
    val cardInformationStateFlow = binRepository.cardInformationStateFlow

    private val binItemListMutableStateFlow = MutableStateFlow<List<BinItem>>(emptyList())
    val binItemListStateFlow = binItemListMutableStateFlow.asStateFlow()


    fun fetchCardInformation(bin: String) {
        if (bin.isNotEmpty()) {
            viewModelScope.launch(dispatcherIO) {
                binRepository.fetchCardInformation(bin)
            }
        }
    }

    private fun fetchBinItemList() {
        viewModelScope.launch(dispatcherIO) {
            binRepository.fetchBinItemList()
                .collect { binItemList ->
                    binItemListMutableStateFlow.value = binItemList
                }
        }
    }

    fun insertBinItem(bin: String) {
        if (bin.isNotEmpty()) {
            viewModelScope.launch(dispatcherIO) {
                binRepository.insertBinItem(bin)
            }
        }
    }

    fun deleteBinItem(binItem: BinItem) {
        viewModelScope.launch(dispatcherIO) {
            binRepository.deleteBinItem(binItem)

        }
    }
}