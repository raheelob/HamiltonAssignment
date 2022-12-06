package com.example.currencyexchange.data.model

import com.google.gson.annotations.SerializedName

data class ErrorData(
    @SerializedName("result"     ) var result     : String? = null,
    @SerializedName("error-type" ) var errorType : String? = null
)