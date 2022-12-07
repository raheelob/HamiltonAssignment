package com.example.currencyexchange

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.currencyexchange.data.service.CurrencyExchangeRateWorker
import com.example.currencyexchange.data.service.CurrencyWorkerFactory
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CurrencyApp : Application(), Configuration.Provider {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    @Inject
    lateinit var useCase: CurrencyUseCase

    override fun getWorkManagerConfiguration() =
       with( Configuration.Builder()){
           setWorkerFactory(CurrencyWorkerFactory(useCase))
           setMinimumLoggingLevel(android.util.Log.DEBUG)
           build()
       }


    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val myWorkRequest = PeriodicWorkRequestBuilder<CurrencyExchangeRateWorker>(5, TimeUnit.HOURS).build()
        with(WorkManager.getInstance(this)) {
            enqueueUniquePeriodicWork(
                CurrencyExchangeRateWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                myWorkRequest
            )
        }
    }
}