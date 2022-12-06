package com.example.currencyexchange.data.repository

import com.example.currencyexchange.data.api.CurrencyService
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(private val api: CurrencyService) : CurrencyRepository {
    override fun getExchangeRate(currency: String): Flow<RemoteData<CurrencyExchangeEntity>> = flow <RemoteData<CurrencyExchangeEntity>>{
        val remoteExchangeRate = with(api.getCurrencyExchangeRate()){
            CurrencyExchangeEntity(
                result = result,
                documentation = documentation,
                termsOfUse = termsOfUse,
                timeLastUpdateUnix = timeLastUpdateUnix,
                timeLastUpdateUtc = timeLastUpdateUtc,
                timeNextUpdateUnix = timeNextUpdateUnix,
                timeNextUpdateUtc = timeNextUpdateUtc,
                conversionRates = conversionRates
            )}
        emit(RemoteData.Success(remoteExchangeRate))
    }
}