package com.outatime.mvvmsecondexample.data

import com.outatime.mvvmsecondexample.data.database.dao.QuoteDao
import com.outatime.mvvmsecondexample.data.database.entities.QuoteEntity
import com.outatime.mvvmsecondexample.data.network.QuoteService
import com.outatime.mvvmsecondexample.domain.model.Quote
import com.outatime.mvvmsecondexample.domain.model.toDomain
import javax.inject.Inject

// This class will determine whether access to the network part (backend, like retrofit), database, and so on
class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteDao: QuoteDao
) {

    // This method will get the quotes from the api and send them to the domain
    suspend fun getAllQuotesFromApi(): List<Quote> {
        // Calling backend in order to get the quotes
        val response = api.getQuotes()

        // Returning the result as a domain object
        return response.map { it.toDomain() }
    }

    //
    suspend fun getAllQuotesFromDatabase(): List<Quote> {
        val response = quoteDao.getAllQuotes()

        return response.map { it.toDomain() }
    }

    suspend fun insertQuotes(quotes: List<QuoteEntity>) {
        quoteDao.insertAll(quotes)
    }

    suspend fun clearQuotes() {
        quoteDao.deleteAllQuotes()
    }
}