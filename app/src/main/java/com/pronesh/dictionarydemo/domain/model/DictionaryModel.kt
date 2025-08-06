package com.pronesh.dictionarydemo.domain.model



data class Meaning(
    val definitions: Definition,
    val partOfSpeech: String,
)

data class WordItem(
    val meanings: List<Meaning>,
    val phonetic: String,
    val word: String
)

data class Definition(
    val definition: String,
    val example: String,
)