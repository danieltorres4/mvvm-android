package com.outatime.mvvmsecondexample.domain

import com.outatime.mvvmsecondexample.data.QuoteRepository
import com.outatime.mvvmsecondexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetRandomQuoteUseCaseTest {
    // Mocking but not instantiated. The instance will be created in the future
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    // This var needs to be real instantiated
    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(quoteRepository)
    }

    @Test
    fun whenDatabaseIsEmptyThenReturnNull() = runBlocking {
        // Given
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns emptyList()

        // When
        val response = getRandomQuoteUseCase()

        // Then
        assert(response == null)
    }

    @Test
    fun whenDatabaseIsNotEmptyThenReturnsQuote() = runBlocking {
        // Given
        val quoteList = listOf(Quote("Legend... wait for it... dary", "Barney Stinson"))
        coEvery { quoteRepository.getAllQuotesFromDatabase() } returns quoteList

        // When
        val response = getRandomQuoteUseCase()

        // Then
        assert(response == quoteList.first())
    }
}