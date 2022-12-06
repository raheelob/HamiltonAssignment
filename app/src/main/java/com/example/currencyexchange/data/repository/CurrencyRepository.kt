package com.example.currencyexchange.data.repository

import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getExchangeRate(fetchLocal: Boolean): Flow<RemoteData<List<CurrencyExchangeEntity>>>
}