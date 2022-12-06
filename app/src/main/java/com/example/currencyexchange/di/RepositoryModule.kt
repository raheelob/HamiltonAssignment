package com.example.currencyexchange.di;

import com.example.currencyexchange.data.repository.CurrencyRepository
import com.example.currencyexchange.data.repository.CurrencyRepositoryImpl
import dagger.Binds
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindCurrencyExchangeRepository(currencyRepositoryImpl: CurrencyRepositoryImpl): CurrencyRepository
}
