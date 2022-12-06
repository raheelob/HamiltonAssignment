package com.example.currencyexchange.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.data.local.converter.ExchangeRateConverter

@Database(entities = [CurrencyExchangeEntity::class], version = 1, exportSchema = false)
@TypeConverters(ExchangeRateConverter::class)
abstract class CurrencyDatabase : RoomDatabase(){
    abstract fun CurrencyDao(): CurrencyDao

    companion object{
        @Volatile
        private var INSTANCE : CurrencyDatabase? = null
        private const val DB_NAME = "currency_database.db"
        fun getInstance(context: Context): CurrencyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}