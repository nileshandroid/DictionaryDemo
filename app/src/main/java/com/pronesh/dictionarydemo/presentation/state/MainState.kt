package com.pronesh.dictionarydemo.presentation.state

import com.pronesh.dictionarydemo.domain.model.WordItem

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
data class MainState(
    val isLoading: Boolean=false,
    val searchWord: String=" ",

    val wordItem: WordItem?= null
)
