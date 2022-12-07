package com.example.currencyexchange.ui.currency.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.currencyexchange.data.api.CurrencyService
import com.example.currencyexchange.data.local.CurrencyDao
import com.example.currencyexchange.data.local.CurrencyDatabase
import com.example.currencyexchange.data.model.ConversionModel
import com.example.currencyexchange.data.model.ErrorData
import com.example.currencyexchange.data.repository.CurrencyRepository
import com.example.currencyexchange.data.repository.CurrencyRepositoryImpl
import com.example.currencyexchange.ui.currency.CoroutineTestRule
import com.example.currencyexchange.ui.currency.event.CurrencyDataEvent
import com.example.currencyexchange.ui.currency.usecase.CurrencyUseCase
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class CurrencyViewModelTest: TestCase() {

    private inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
    private val apiService = mock<CurrencyService>()
    private lateinit var viewModel: CurrencyViewModel
    private lateinit var currencyRepository:CurrencyRepository
    private lateinit var useCase: CurrencyUseCase
    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: CurrencyDatabase
    private lateinit var context: Context

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        currencyDao = db.CurrencyDao()
        currencyRepository = CurrencyRepositoryImpl(apiService, currencyDao)
        useCase = CurrencyUseCase(currencyRepository)
        viewModel = CurrencyViewModel(useCase)
    }

    @After
    @Throws(Exception::class)
    fun tearDownClass() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun testErrorEvent() = kotlinx.coroutines.test.runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currencyExchangeRateTasksEvent.collect {
                when (it) {
                    is CurrencyDataEvent.ConvertedAmount -> {}
                    is CurrencyDataEvent.Error -> it.errorData.errorType?.let { errorStr -> assertTrue(errorStr.isNotEmpty()) }
                    CurrencyDataEvent.Loading -> {}
                    CurrencyDataEvent.RemoteErrorByNetwork -> {}
                    CurrencyDataEvent.showFromCurrencySelection -> {}
                    CurrencyDataEvent.showToCurrencySelection -> {}
                    CurrencyDataEvent.swapCurrency -> {}
                }
            }
        }
        viewModel.sendErrorEvent(
            ErrorData(
                result = "Please enter a valid amount",
                errorType = "Invalid Amount"
            )
        )
        collectJob.cancel()
    }

    @Test
    fun testEvent() = kotlinx.coroutines.test.runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.currencyExchangeRateTasksEvent.collect {
                when (it) {
                    is CurrencyDataEvent.ConvertedAmount -> {
                        assertTrue(it.data.currencyConverted > 0)
                    }
                    is CurrencyDataEvent.Error -> {}
                    CurrencyDataEvent.Loading -> {}
                    CurrencyDataEvent.RemoteErrorByNetwork -> {}
                    CurrencyDataEvent.showFromCurrencySelection -> {}
                    CurrencyDataEvent.showToCurrencySelection -> {}
                    CurrencyDataEvent.swapCurrency -> {}
                }
            }
        }
        viewModel.sendConvertedAmount(ConversionModel(currencyConverted = 150,
            currencyConvertedType = "GBP",
            currencyToConvert = 180,
            currencyToConvertType = "USE",
            conversionRate = 1.22))
        collectJob.cancel()
    }

}