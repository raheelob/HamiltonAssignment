package com.example.currencyexchange.data.local.converter

import androidx.room.TypeConverter
import com.example.currencyexchange.data.model.ConversionRatesModel
import com.google.gson.Gson

class ExchangeRateConverter {

    @TypeConverter
    fun objectToJson(value: Any?): String = Gson().toJson(value)

    @TypeConverter
    fun stringToConversionbRateObject(string: String?): ConversionRatesModel? {
        return Gson().fromJson(string, ConversionRatesModel::class.java)
    }
}