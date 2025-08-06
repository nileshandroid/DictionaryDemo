package com.pronesh.dictionarydemo.data.api

import com.pronesh.dictionarydemo.data.dto.DictionaryDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */

interface DictionaryAPI {

    @GET("{word}")
    suspend fun getDictionaryWord(@Path("word") word: String): DictionaryDto?
}