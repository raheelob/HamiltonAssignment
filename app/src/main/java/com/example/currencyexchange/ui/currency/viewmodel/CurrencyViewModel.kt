package com.example.currencyexchange.ui.currency.viewmodel

import android.os.CountDownTimer
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.data.model.ConversionRatesModel
import com.example.currencyexchange.data.model.ErrorData
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.event.TimerEvent
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import com.example.currencyexchange.utils.enums.RepoCallType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    //To hold the last state of timer...
    private val _timerState:MutableStateFlow<TimerEvent> = MutableStateFlow(TimerEvent.InitialState)
    val timerState: StateFlow<TimerEvent> = _timerState
    /***********************************************************************************/
    private var countDownTimer:CountDownTimer? = null

    fun setTimerToInitialState(){//update timer state once it crosses 30s, and required action already completed...
        _timerState.value = TimerEvent.InitialState
    }

    fun startTimer() {
        countDownTimer = object : CountDownTimer(31000, 1000) {
            //one second it takes to reach next fragment, so timer starting from 31s...
            override fun onTick(millisUntilFinished: Long) {
                _timerState.value = TimerEvent.OnTick("" + millisUntilFinished / 1000 + " s")
            }

            override fun onFinish() {
                _timerState.value = TimerEvent.Finish
            }
        }.start()
    }

    fun cancelTimer() = countDownTimer?.cancel()

    fun validateAmount(fromCurrency: AppCompatTextView, toCurrency: AppCompatTextView, amountToConvert: AppCompatEditText){
        if(amountToConvert.text.toString().isEmpty() || amountToConvert.text.toString().toDouble() == 0.0){
            showError(ErrorData(
                result = "Please enter a valid amount",
                errorType = "Invalid Amount"
            ))
        }else{
            if(amountToConvert.text.toString().toDouble() > 0) {
                fetchCurrencyExchangeRate(
                    fromCurrency = fromCurrency.text.toString(),
                    toCurrency = toCurrency.text.toString(),
                    amountToConvert = amountToConvert.text.toString().toDouble(),
                )
            }
        }
    }

      private fun fetchCurrencyExchangeRate(fromCurrency: String, toCurrency: String, amountToConvert: Double) = viewModelScope.launch {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.Loading)
        currencyUseCase.execute(CurrencyUseCase.Params(currency = "USD", type = RepoCallType.VIEW_MODEL))
            .collect { response ->
                when (response) {

                    is RemoteData.Success -> response.value?.let {
                        it.conversionRates?.let { rates ->
                            convertCurrency(fromCurrency, toCurrency,
                                rates, amountToConvert)
                        }
                    }

                    is RemoteData.RemoteErrorByNetwork -> {
                        sendRemoteErrorByNetworkEvent()
                    }

                    is RemoteData.Error -> response.error?.let {
                        sendErrorEvent(it)
                    }
                }
            }
    }

     private fun convertCurrency(fromCurrency: String, toCurrency: String, conversionRatesModel: ConversionRatesModel, amountToConvert : Double) {
        val fromCurrencyObject = ConversionRatesModel::class.memberProperties.find { it.name == fromCurrency }?.get(conversionRatesModel) as Double
        val toCurrencyObject = ConversionRatesModel::class.memberProperties.find { it.name == toCurrency }?.get(conversionRatesModel) as Double
        val convertedMoney = (amountToConvert * toCurrencyObject)/fromCurrencyObject
        viewModelScope.launch {
             sendConvertedAmount( ConversionModel(amountToConvert, fromCurrency, convertedMoney, toCurrency, (toCurrencyObject/ fromCurrencyObject) ))
         }
    }

    fun showFromDialog() = viewModelScope.launch { sendShowFromDialogEvent() }

    fun showToDialog() = viewModelScope.launch { sendToDialogEvent() }

    fun swapCurrency() = viewModelScope.launch { swapCurrencyEvent() }

    private fun showError(errorData: ErrorData) = viewModelScope.launch { sendErrorEvent(errorData) }

    internal suspend fun sendConvertedAmount( data: ConversionModel) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.ConvertedAmount(data))
    }

    private suspend fun sendShowFromDialogEvent( ) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.showFromCurrencySelection)
    }

    private suspend fun sendToDialogEvent( ) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.showToCurrencySelection)
    }

    private suspend fun swapCurrencyEvent( ) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.swapCurrency)
    }

    private suspend fun sendRemoteErrorByNetworkEvent( ) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.RemoteErrorByNetwork)
    }

    internal suspend fun sendErrorEvent(errorData: ErrorData ) {
        currencyExchangeRateEventChannel.send(CurrencyDataEvent.Error(errorData))
    }

}