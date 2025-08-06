package com.pronesh.dictionarydemo.data.mapper

import com.pronesh.dictionarydemo.data.dto.DefinitionDto
import com.pronesh.dictionarydemo.data.dto.MeaningDto
import com.pronesh.dictionarydemo.data.dto.WordItemDto
import com.pronesh.dictionarydemo.domain.model.Definition
import com.pronesh.dictionarydemo.domain.model.Meaning
import com.pronesh.dictionarydemo.domain.model.WordItem

/**
 * Created by Nilesh Salunkhe on 06-08-2025.
 */

fun WordItemDto.toWordItem() = WordItem(
    word = word ?: "",
    meanings = meanings?.map {
        it.toMeaning()
    } ?: emptyList(),
    phonetic = phonetic ?: ""
)

fun MeaningDto.toMeaning() = Meaning(
    definitions = definitionDtoToDefinition(definitions?.get(0)),
    partOfSpeech = partOfSpeech ?: ""
)

fun definitionDtoToDefinition(definitionDto: DefinitionDto?) = Definition(
    definition = definitionDto?.definition ?: "",
    example = definitionDto?.example ?: ""
)