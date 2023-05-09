package com.outatime.mvvmsecondexample.domain

import com.outatime.mvvmsecondexample.data.QuoteRepository
import com.outatime.mvvmsecondexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetQuotesUseCaseTest {
    // Mocking but not instantiated. The instance will be created in the future
    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    // This var needs to be real instantiated
    lateinit var getQuotesUseCase: GetQuotesUseCase

    // The test may require an initial configuration. So, the code inside the following function
    // will be executed before the tests. +
    @Before
    fun onBefore() {
        // Initializing mock library
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun whenTheApiDoesNotReturnAnythingThenReturnValuesFromDatabaseTest() = runBlocking {
        // Given
        // Every time the real use case calls the getAllQuotesFromApi method, an empty list will be returned
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        // When -> when the use case is called, the code lines behind will be executed
        getQuotesUseCase()

        // Then -> what happens after the use case is called
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
        coVerify(exactly = 0) { quoteRepository.clearQuotes() }
        coVerify(exactly = 0) { quoteRepository.insertQuotes(any()) }
    }

    @Test
    fun whenTheApuReturnsSomethingThenGetValuesFromTheApiTest() = runBlocking {
        // Given
        val myList = listOf(Quote("Hello World", "Daniel"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns myList

        // When -> Saving the response in order to check if this response is the same as myList
        val response = getQuotesUseCase()

        // Then -> verifying if the methods are called...
        coVerify(exactly = 1) { quoteRepository.clearQuotes() }
        coVerify(exactly = 1) { quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 0) { quoteRepository.getAllQuotesFromDatabase() }

        // Verifying is myList is similar to response
        assert(myList == response)
    }
}