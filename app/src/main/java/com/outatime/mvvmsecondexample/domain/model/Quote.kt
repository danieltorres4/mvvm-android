package com.outatime.mvvmsecondexample.domain.model

import com.outatime.mvvmsecondexample.data.database.entities.QuoteEntity
import com.outatime.mvvmsecondexample.data.models.QuoteModel

// Final model. UI and Domain will work with this model
data class Quote(
    val quote: String,
    val author: String
)

fun QuoteModel.toDomain() = Quote(quote, author)

fun QuoteEntity.toDomain() = Quote(quote, author)
