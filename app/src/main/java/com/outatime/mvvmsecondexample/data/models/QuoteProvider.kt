package com.outatime.mvvmsecondexample.data.models

import javax.inject.Inject
import javax.inject.Singleton

// This class emulates Room or Retrofit
// Using Singleton label in order to avoid two quoteProvider instances: one with the quotes and the
// other one without quotes.
@Singleton
class QuoteProvider @Inject constructor() {
    var quotes: List<QuoteModel> = emptyList()
}