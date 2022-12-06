package com.example.currencyexchange.data.api

import com.example.currencyexchange.data.dtos.CurrencyExchangeDTO
import retrofit2.http.GET

interface CurrencyService {

    @GET("/latest/USD")
    suspend fun getCurrencyExchangeRate(): CurrencyExchangeDTO

}