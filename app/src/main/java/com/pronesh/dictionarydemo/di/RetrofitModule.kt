package com.pronesh.dictionarydemo.di

import com.pronesh.dictionarydemo.data.api.DictionaryAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    fun provideBaseUrl() = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseURL: String) = Retrofit
        .Builder()
        .baseUrl(baseURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    fun provideDictionaryAPI(retrofit: Retrofit) = retrofit.create(DictionaryAPI::class.java)
}

