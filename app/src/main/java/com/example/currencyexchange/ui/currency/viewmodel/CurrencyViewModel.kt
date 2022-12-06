package com.example.currencyexchange.ui.currency.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : ViewModel() {
    // currency exchange rate event...
    /***********************************************************************************/
    private val currencyExchangeRateEventChannel = Channel<CurrencyDataEvent>()
    internal val currencyExchangeRateTasksEvent = currencyExchangeRateEventChannel.receiveAsFlow()
    /***********************************************************************************/

     fun fetchCurrencyExchangeRate(currency: String) = viewModelScope.launch {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.Loading)
        currencyUseCase.execute(CurrencyUseCase.Params(currency = currency))
            .collect { response ->
                when (response) {
                    RemoteData.Loading -> {
                        Log.d("","")
                    }

                    is RemoteData.Success -> response.value?.let {
                        Log.d("",""+it.conversionRates)
                    }

                    is RemoteData.RemoteErrorByNetwork -> {
                        Log.d("","")
                    }

                    is RemoteData.Error -> response.error?.let {
                        Log.d("","")
                    }
                }
            }
    }

}