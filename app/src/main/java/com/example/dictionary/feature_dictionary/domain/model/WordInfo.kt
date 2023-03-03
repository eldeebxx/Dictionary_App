package com.example.dictionary.feature_dictionary.domain.model

data class WordInfo(
    val meanings: List<Meaning>,
    val sourceUrls: List<String>,
    val phonetic: String,
    val word: String
)
