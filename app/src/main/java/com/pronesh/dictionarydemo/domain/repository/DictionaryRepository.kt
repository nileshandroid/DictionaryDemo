package com.pronesh.dictionarydemo.domain.repository


import com.pronesh.dictionarydemo.domain.model.WordItem
import com.pronesh.dictionarydemo.utils.ResponseResult
import kotlinx.coroutines.flow.Flow

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
interface DictionaryRepository {

    suspend fun getWordResult(word:String): Flow<ResponseResult<WordItem>>
}