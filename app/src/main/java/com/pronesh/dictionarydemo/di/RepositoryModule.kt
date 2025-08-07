package com.pronesh.dictionarydemo.di

import com.pronesh.dictionarydemo.data.repository.DictionaryRepositoryImpl
import com.pronesh.dictionarydemo.domain.repository.DictionaryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindDictionaryRepository(dictionaryRepositoryImpl: DictionaryRepositoryImpl)
    : DictionaryRepository
}