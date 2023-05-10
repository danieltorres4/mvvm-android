package com.outatime.mvvmsecondexample.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.outatime.mvvmsecondexample.domain.GetQuotesUseCase
import com.outatime.mvvmsecondexample.domain.GetRandomQuoteUseCase
import com.outatime.mvvmsecondexample.domain.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    // Use Cases: when the view model is created, the following variables will be injected
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase
): ViewModel() {
    val quoteModel = MutableLiveData<Quote>() // model live data, this one will be painted in the screen
    val isLoading = MutableLiveData<Boolean>() // in order to show or hide the progress bar

    fun onCreate() {
        // This method will case the use case in order to get all the quotes
        // Calling a coroutine...
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getQuotesUseCase()

            if (!result.isNullOrEmpty()) {
                quoteModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }
    }

    // When the user taps the screen, we'll call this function from the activity in order to get a random quote
    // Then, quoteModel will notify the activity that a change is oncoming
    fun randomQuote() {
        viewModelScope.launch {
            isLoading.postValue(true)

            // Calling a use case to get a random quote
            val quote = getRandomQuoteUseCase()

            if(quote != null) {
                quoteModel.postValue(quote)
            }

            isLoading.postValue(false)
        }
    }
}