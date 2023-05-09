package com.outatime.mvvmsecondexample.domain

import com.outatime.mvvmsecondexample.data.QuoteRepository
import com.outatime.mvvmsecondexample.domain.model.Quote
import javax.inject.Inject

class GetRandomQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): Quote? {
        // Getting the list from the db
        val quotes = repository.getAllQuotesFromDatabase()

        if(!quotes.isNullOrEmpty()) {
            val randomNumber = (quotes.indices).random()
            return quotes[randomNumber]
        }

        return null
    }
}