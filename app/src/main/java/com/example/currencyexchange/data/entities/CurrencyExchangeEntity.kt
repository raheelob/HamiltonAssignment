package com.example.currencyexchange.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyexchange.data.model.ConversionRatesModel
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity
@Parcelize
data class CurrencyExchangeEntity(
    @SerializedName("result"                ) var result             : String?          = null,
    @SerializedName("documentation"         ) var documentation      : String?          = null,
    @SerializedName("terms_of_use"          ) var termsOfUse         : String?          = null,
    @PrimaryKey
    @SerializedName("time_last_update_unix" ) var timeLastUpdateUnix : Int?             = null,
    @SerializedName("time_last_update_utc"  ) var timeLastUpdateUtc  : String?          = null,
    @SerializedName("time_next_update_unix" ) var timeNextUpdateUnix : Int?             = null,
    @SerializedName("time_next_update_utc"  ) var timeNextUpdateUtc  : String?          = null,
    @SerializedName("conversion_rates"      ) var conversionRates    : @RawValue ConversionRatesModel? = ConversionRatesModel(),
    @SerializedName("base_code"             ) var baseCode           : String?          = null)
    :Parcelable{
    constructor() : this(result = "", documentation = "", termsOfUse = "", timeLastUpdateUnix = 0, timeLastUpdateUtc = "")
    }
