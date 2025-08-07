package com.pronesh.dictionarydemo.data.repository

import android.app.Application
import com.pronesh.dictionarydemo.data.api.DictionaryAPI
import com.pronesh.dictionarydemo.data.mapper.toWordItem
import com.pronesh.dictionarydemo.domain.model.WordItem
import com.pronesh.dictionarydemo.domain.repository.DictionaryRepository
import com.pronesh.dictionarydemo.utils.ResponseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */

class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryAPI: DictionaryAPI,
    private val application: Application,
) : DictionaryRepository {
    override suspend fun getWordResult(word: String): Flow<ResponseResult<WordItem>> {
        return flow {
            emit(ResponseResult.Loading(true))
            val remoteWordResultDto = try {
                dictionaryAPI.getDictionaryWord(word)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ResponseResult.Error("IO Exception"))
                emit(ResponseResult.Loading(false))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResponseResult.Error("Exception"))
                emit(ResponseResult.Loading(false))
                return@flow
            }
            remoteWordResultDto?.let { result ->
                result[0]?.let { word ->
                    emit(ResponseResult.Success(word?.toWordItem()))
                    emit(ResponseResult.Loading(false))
                    return@flow
                }
            }
            emit(ResponseResult.Error("No Record Found"))
            emit(ResponseResult.Loading(true))
        }
    }

}