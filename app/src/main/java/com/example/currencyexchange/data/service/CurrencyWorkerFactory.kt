package com.example.currencyexchange.data.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase

class CurrencyWorkerFactory(private val useCase: CurrencyUseCase) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return CurrencyExchangeRateWorker(appContext, workerParameters, useCase)

    }
}