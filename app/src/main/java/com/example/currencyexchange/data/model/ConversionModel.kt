package com.example.currencyexchange.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConversionModel(
    val currencyToConvert: Int,
    val currencyToConvertType: String,
    val currencyConverted: Int,
    val currencyConvertedType: String
) : Parcelable
