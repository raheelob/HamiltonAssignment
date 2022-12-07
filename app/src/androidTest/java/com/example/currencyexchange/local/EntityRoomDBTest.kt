package com.example.currencyexchange.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.currencyexchange.data.entities.CurrencyExchangeEntity
import com.example.currencyexchange.data.local.CurrencyDao
import com.example.currencyexchange.data.local.CurrencyDatabase
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class EntityRoomDBTest {

    private lateinit var currencyDao: CurrencyDao
    private lateinit var db: CurrencyDatabase
    private lateinit var context: Context
    private lateinit var currencyExchangeEntity: CurrencyExchangeEntity

    @Before
    fun createDb() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        currencyDao = db.CurrencyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun readAndWriteExchangeRate() = runBlocking {
        currencyExchangeEntity = CurrencyExchangeEntity()
        currencyDao.insertExchangeRateIntoDB(currencyExchangeEntity)//write operation...
        val data = currencyDao.getExchangeRateFromDB()//read operation
        assertTrue(data[0].conversionRates != null)
    }

}





