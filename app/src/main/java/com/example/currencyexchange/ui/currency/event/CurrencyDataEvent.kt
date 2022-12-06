package com.example.currencyexchange.ui.currency.event

import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.data.model.ErrorData

sealed class CurrencyDataEvent {
    object RemoteErrorByNetwork : CurrencyDataEvent()
    object Loading : CurrencyDataEvent()
    class Error(val errorData: ErrorData) : CurrencyDataEvent()
    data class Success(val data: CurrencyExchangeEntity) : CurrencyDataEvent()
}
