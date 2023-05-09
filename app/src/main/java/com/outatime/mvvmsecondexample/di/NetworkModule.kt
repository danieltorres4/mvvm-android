package com.outatime.mvvmsecondexample.di

import com.outatime.mvvmsecondexample.data.network.QuoteApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// This object is a module. Its function lies in providing dependencies not to easy to provide like
// libraries or class dependencies that contains interfaces
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // Providing retrofit... If any of our classes needs a retrofit instance, Dagger is able to provide it.
    // But every time a class requests for a retrofit instance, a new instance will be created. This lies in unnecessary
    // memory consumption. So, we use the @Singleton label in order to keep an unique retrofit instance for
    // every class that requests it.
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://drawsomething-59328-default-rtdb.europe-west1.firebasedatabase.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // The following function receives a parameter: retrofit. This parameter will be obtained from the
    // provideRetrofit() provider
    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): QuoteApiClient {
        return retrofit.create(QuoteApiClient::class.java)
    }
}