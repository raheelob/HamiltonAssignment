package com.example.currencyexchange.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConversionModel(
    val currencyToConvert: Double,
    val currencyToConvertType: String,
    val currencyConverted: Double,
    val currencyConvertedType: String,
    val conversionRate : Double
) : Parcelable
