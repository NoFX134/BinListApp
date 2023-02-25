package com.binlistapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import com.binlistapp.data.BinRepository
import com.binlistapp.data.model.entities.BinItem
import com.binlistapp.di.qualifiers.dispatchers.DispatcherIO
import com.binlistapp.utils.functions.ifDebug
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class BinCheckViewModel @Inject constructor(
    private val binRepository: BinRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : ViewModel() {

    init {
        fetchBinItemList()
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        ifDebug { Log.e("COROUTINE_EXCEPTION", "$throwable\n${throwable.stackTraceToString()}") }
    }

    val loadingStateFlow = binRepository.loadingStateFlow
    val errorSharedFlow = binRepository.errorSharedFlow
    val cardInformationStateFlow = binRepository.cardInformationStateFlow

    private val binItemListMutableStateFlow = MutableStateFlow<List<BinItem>>(emptyList())
    val binItemListStateFlow = binItemListMutableStateFlow.asStateFlow()


    fun fetchCardInformation(bin: String) {
        viewModelScope.launch(dispatcherIO) {
            binRepository.fetchCardInformation(bin)
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
        viewModelScope.launch(dispatcherIO) {
            binRepository.insertBinItem(bin)
        }
    }
}