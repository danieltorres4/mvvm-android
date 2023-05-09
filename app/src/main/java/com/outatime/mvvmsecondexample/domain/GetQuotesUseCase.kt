package com.outatime.mvvmsecondexample.domain

import com.outatime.mvvmsecondexample.data.QuoteRepository
import com.outatime.mvvmsecondexample.data.database.entities.toDatabase
import com.outatime.mvvmsecondexample.domain.model.Quote
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository){
    // This method will always return what the use case requires. In this case, the use case will return
    // the info from the server (only the very first time)
    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()

        return if(quotes.isNotEmpty()) {
            // Happy path -> Inserting all quotes in the db
            // The following code line is very important due to avoid inserting infinity times all the quotes.
            // So, every time the app requests the service, the quotes will be deleted before inserting the new ones
            repository.clearQuotes()
            repository.insertQuotes(quotes.map {
                it.toDatabase()
            })
            quotes
        } else {
            // The else statement will return the saved info from database in case the call from the API
            // is not successful
            repository.getAllQuotesFromDatabase()
        }
    }
}