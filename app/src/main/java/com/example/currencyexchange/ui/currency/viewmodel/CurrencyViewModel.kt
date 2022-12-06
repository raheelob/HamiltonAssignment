package com.example.currencyexchange.ui.currency.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.model.ConversionRatesModel
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import com.example.currencyexchange.utils.CurrencyName
import com.example.currencyexchange.utils.RepoCallType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.full.memberProperties


@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: CurrencyUseCase
) : ViewModel() {
    // currency exchange rate event...
    /***********************************************************************************/
    private val currencyExchangeRateEventChannel = Channel<CurrencyDataEvent>()
    internal val currencyExchangeRateTasksEvent = currencyExchangeRateEventChannel.receiveAsFlow()
    /***********************************************************************************/

      fun fetchCurrencyExchangeRate(currency: String, amountToConvert: Double) = viewModelScope.launch {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.Loading)
        currencyUseCase.execute(CurrencyUseCase.Params(currency = currency, type = RepoCallType.VIEW_MODEL))
            .collect { response ->
                when (response) {
                    RemoteData.Loading -> {
                        Log.d("","")
                    }

                    is RemoteData.Success -> response.value?.let {
                        Log.d("",""+it.conversionRates)
                        convertCurrency(CurrencyName.USD.name, CurrencyName.AED.name, it.conversionRates!!, amountToConvert)
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

    private fun convertCurrency(fromCurrency: String, toCurrency: String, conversionRatesModel: ConversionRatesModel, amountToConvert : Double) {
        val fromCurrencyObject = ConversionRatesModel::class.memberProperties.find { it.name == fromCurrency }?.get(conversionRatesModel) as Double
        val toCurrencyObject = ConversionRatesModel::class.memberProperties.find { it.name == toCurrency }?.get(conversionRatesModel) as Double
        val conversionMoney = (amountToConvert * toCurrencyObject)/fromCurrencyObject
//        Log.d("Robert","converted money ====>> $conversionMoney")
    }

}