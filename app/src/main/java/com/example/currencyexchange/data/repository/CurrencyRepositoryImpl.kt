package com.example.currencyexchange.data.repository

import com.example.currencyexchange.data.api.CurrencyService
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.data.local.CurrencyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val api: CurrencyService,
    private val dao: CurrencyDao
) : CurrencyRepository {
    override fun getExchangeRate(currency: String): Flow<RemoteData<CurrencyExchangeEntity>> =
        flow<RemoteData<CurrencyExchangeEntity>> {
            val localExchangeRate = dao.getExchangeRateFromDB()
            if (localExchangeRate.isNotEmpty()) {
                emit(RemoteData.Success(localExchangeRate.get(0)))
            } else {
                val remoteExchangeRate = with(api.getCurrencyExchangeRate()) {
                    CurrencyExchangeEntity(
                        result = result,
                        documentation = documentation,
                        termsOfUse = termsOfUse,
                        timeLastUpdateUnix = timeLastUpdateUnix,
                        timeLastUpdateUtc = timeLastUpdateUtc,
                        timeNextUpdateUnix = timeNextUpdateUnix,
                        timeNextUpdateUtc = timeNextUpdateUtc,
                        conversionRates = conversionRates
                    )
                }
                dao.insertExchangeRateIntoDB(data = remoteExchangeRate)
                emit(RemoteData.Success(remoteExchangeRate))
            }
        }.flowOn(Dispatchers.IO)
}