package com.example.currencyexchange.data.dtos

import com.example.currencyexchange.data.model.ConversionRatesModel
import com.google.gson.annotations.SerializedName


data class CurrencyExchangeDTO (

  @SerializedName("result"                ) var result             : String?          = null,
  @SerializedName("documentation"         ) var documentation      : String?          = null,
  @SerializedName("terms_of_use"          ) var termsOfUse         : String?          = null,
  @SerializedName("time_last_update_unix" ) var timeLastUpdateUnix : Int?             = null,
  @SerializedName("time_last_update_utc"  ) var timeLastUpdateUtc  : String?          = null,
  @SerializedName("time_next_update_unix" ) var timeNextUpdateUnix : Int?             = null,
  @SerializedName("time_next_update_utc"  ) var timeNextUpdateUtc  : String?          = null,
  @SerializedName("base_code"             ) var baseCode           : String?          = null,
  @SerializedName("conversion_rates"      ) var conversionRates    : ConversionRatesModel? = ConversionRatesModel()

)