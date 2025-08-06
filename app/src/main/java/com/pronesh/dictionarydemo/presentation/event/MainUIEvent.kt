package com.pronesh.dictionarydemo.presentation.event

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
sealed class MainUIEvent {
    data class OnSearchWord(val newWord:String): MainUIEvent()
    object OnSearchClick: MainUIEvent()
}