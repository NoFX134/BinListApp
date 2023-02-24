package com.binlistapp.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import com.binlistapp.data.BinRepository
import com.binlistapp.di.qualifiers.dispatchers.DispatcherIO
import com.binlistapp.utils.functions.ifDebug
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class BinCheckViewModel @Inject constructor(
    private val binRepository: BinRepository,
    @DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : ViewModel() {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        ifDebug { Log.e("COROUTINE_EXCEPTION", "$throwable\n${throwable.stackTraceToString()}") }
    }

    val loadingStateFlow = binRepository.loadingStateFlow
    val errorSharedFlow = binRepository.errorSharedFlow
    val cardInformationStateFlow = binRepository.cardInformationStateFlow

    fun fetchCardInformation(bin: String) {
        viewModelScope.launch(dispatcherIO + coroutineExceptionHandler) {
            binRepository.fetchCardInformation(bin)
        }
    }
}