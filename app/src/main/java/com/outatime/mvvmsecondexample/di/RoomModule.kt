package com.outatime.mvvmsecondexample.di

import android.content.Context
import androidx.room.Room
import com.outatime.mvvmsecondexample.data.database.QuoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "quote_database"

    // Injecting the database...
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, QuoteDatabase::class.java, QUOTE_DATABASE_NAME).build()

    // Injecting the DAO...
    @Singleton
    @Provides
    fun providesQuoteDao(db: QuoteDatabase) = db.getQuoteDao()

}