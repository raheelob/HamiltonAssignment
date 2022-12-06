package com.example.currencyexchange.ui.currency.usecase

import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.api.UseCaseExecutor
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.data.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyUseCase @Inject constructor(private val repo: CurrencyRepository) :
    UseCaseExecutor<CurrencyUseCase.Params, CurrencyExchangeEntity>() {
    override fun runUseCase(parameter: Params?): Flow<RemoteData<CurrencyExchangeEntity>> {
        return repo.getExchangeRate(currency = parameter?.currency ?: "")
    }

    data class Params constructor(
        val currency: String
    ) {
        companion object {
            fun create(
                currency: String
            ) = Params(currency)
        }
    }
}