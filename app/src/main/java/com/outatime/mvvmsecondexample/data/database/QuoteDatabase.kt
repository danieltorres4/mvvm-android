package com.outatime.mvvmsecondexample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.outatime.mvvmsecondexample.data.database.dao.QuoteDao
import com.outatime.mvvmsecondexample.data.database.entities.QuoteEntity

// For each existing DAO, there will be an abstract function
@Database(entities = [QuoteEntity::class], version = 1)
abstract class QuoteDatabase: RoomDatabase() {

    abstract fun getQuoteDao(): QuoteDao
}