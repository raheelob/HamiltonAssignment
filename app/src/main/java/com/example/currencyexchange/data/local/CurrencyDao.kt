package com.example.currencyexchange.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity

@Dao
interface CurrencyDao {

    //Add currency...
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExchangeRateIntoDB(data: CurrencyExchangeEntity)

    //Get currency...
    @Query("Select * from CurrencyExchangeEntity")
    fun getExchangeRateFromDB():List< CurrencyExchangeEntity>
}