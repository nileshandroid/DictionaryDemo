package com.pronesh.dictionarydemo.data.dto

class DictionaryDto : ArrayList<WordItemDto>()

data class MeaningDto(
    val definitions: List<DefinitionDto?>?= null,
    val partOfSpeech: String?= null,
)

data class WordItemDto(
    val meanings: List<MeaningDto>?= null,
    val phonetic: String?= null,
    val word: String?= null
)

data class DefinitionDto(
    val definition: String?= null,
    val example: String?= null,
)