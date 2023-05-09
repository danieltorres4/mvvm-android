package com.outatime.mvvmsecondexample.data.network

import com.outatime.mvvmsecondexample.data.models.QuoteModel
import retrofit2.Response
import retrofit2.http.GET

interface QuoteApiClient {
    @GET("/.json")
    suspend fun getAllQuotes(): Response<List<QuoteModel>>
}