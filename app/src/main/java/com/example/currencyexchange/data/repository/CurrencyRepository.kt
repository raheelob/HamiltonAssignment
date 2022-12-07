package com.example.currencyexchange.data.repository

import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.utils.enums.RepoCallType
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {
    fun getExchangeRate(currency: String, type : RepoCallType): Flow<RemoteData<CurrencyExchangeEntity>>
}