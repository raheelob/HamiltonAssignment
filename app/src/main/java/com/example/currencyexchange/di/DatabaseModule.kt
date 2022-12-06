package com.example.currencyexchange.di

import android.content.Context
import com.example.currencyexchange.data.local.CurrencyDao
import com.example.currencyexchange.data.local.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return CurrencyDatabase.getInstance(context)
    }

    @Provides
    fun provideCurrencyDao(appDatabase: CurrencyDatabase): CurrencyDao {
        return appDatabase.CurrencyDao()
    }

}
