package com.example.currencyexchange.data.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.currencyexchange.data.api.RemoteData
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import com.example.currencyexchange.utils.enums.RepoCallType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class CurrencyExchangeRateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCase: CurrencyUseCase
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "com.example.currencyexchange.data.service.CurrencyExchangeRateWorker"
    }

    override suspend fun doWork(): Result {
          try {
            useCase.execute(CurrencyUseCase.Params(currency = "USD", type = RepoCallType.WORKER))
                .collect { response ->
                    when (response) {
                        is RemoteData.Success -> response.value?.let {
                            Result.success()
                        }

                        is RemoteData.RemoteErrorByNetwork -> {
                            Result.failure()
                        }

                        is RemoteData.Error -> response.error?.let {
                            Result.failure()
                        }
                        else -> {
                            Result.failure()
                        }
                    }
                }
              return Result.success()
          }
          catch (throwable: Throwable){
              return Result.failure()
          }
    }
}