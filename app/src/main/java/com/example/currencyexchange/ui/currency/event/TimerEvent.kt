package com.example.currencyexchange.ui.currency.event

sealed class TimerEvent {
    class OnTick(val timeValue: String) : TimerEvent()
    object Finish : TimerEvent()
    object InitialState : TimerEvent()
}
