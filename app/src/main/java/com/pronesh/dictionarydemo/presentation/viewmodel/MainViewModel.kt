package com.pronesh.dictionarydemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pronesh.dictionarydemo.domain.repository.DictionaryRepository
import com.pronesh.dictionarydemo.presentation.event.MainUIEvent
import com.pronesh.dictionarydemo.presentation.state.MainState
import com.pronesh.dictionarydemo.utils.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val dictionaryRepository: DictionaryRepository) :
    ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var searchJob: Job? = null

    fun onEvent(mainUIEvent: MainUIEvent) {
        when (mainUIEvent) {
            MainUIEvent.OnSearchClick -> {
                searchJob?.cancel()
                searchJob= viewModelScope.launch {
                    loadWordResult()
                }
            }

            is MainUIEvent.OnSearchWord -> {
                _mainState.update {
                    it.copy(searchWord = mainUIEvent.newWord.lowercase())
                }
            }
        }
    }

    private fun loadWordResult() {
        viewModelScope.launch {
            dictionaryRepository.getWordResult(_mainState.value.searchWord)
                .collect { result -> when(result) {
                    is ResponseResult.Error-> Unit
                    is ResponseResult.Loading -> _mainState.update {it.copy(isLoading = result.isLoading)  }
                    is ResponseResult.Success -> {
                        result.data?.let { wordItem->
                            _mainState.update { it.copy(wordItem =wordItem ) }
                        }
                    }
                    }
                }
                }
    }

}