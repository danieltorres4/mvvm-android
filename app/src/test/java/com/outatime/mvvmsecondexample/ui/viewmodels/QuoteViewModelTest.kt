package com.outatime.mvvmsecondexample.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.outatime.mvvmsecondexample.domain.GetQuotesUseCase
import com.outatime.mvvmsecondexample.domain.GetRandomQuoteUseCase
import com.outatime.mvvmsecondexample.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {
    @RelaxedMockK
    private lateinit var getQuotesUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuotesUseCase, getRandomQuoteUseCase)

        // Mocking the dispatcher...
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenViewModelIsCreatedForTheFirstTimeGetAllQuotesAndSetTheFirstValue() = runTest {
        // Given
        val quoteList = listOf(Quote("Hello World", "Daniel"), Quote("Legendary", "Barney Stinson"))
        coEvery { getQuotesUseCase() } returns quoteList

        // When
        quoteViewModel.onCreate()

        // Then
        assert(quoteViewModel.quoteModel.value == quoteList.first()) // verifying that the live data has the same value as the quoteList
    }

    @Test
    fun whenRandomQuoteUseCaseReturnsAQuoteSetOnTheLiveData() = runTest {
        // Given
        val quote = Quote("Hello World", "Daniel")
        coEvery { getRandomQuoteUseCase() } returns quote

        // When
        quoteViewModel.randomQuote()

        // Then -> Verifying the livedata
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun ifRandomQuoteUseCaseReturnsNullKeepTheLastValue() = runTest {
        // Given
        val quote = Quote("Hello World", "Daniel")
        quoteViewModel.quoteModel.value = quote
        coEvery { getRandomQuoteUseCase() } returns null
        // When
        quoteViewModel.randomQuote()

        // Then
        assert(quoteViewModel.quoteModel.value == quote)
    }
}