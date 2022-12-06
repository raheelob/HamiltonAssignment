package com.example.currencyexchange.data.api

import com.example.currencyexchange.data.model.ErrorData

sealed class RemoteData<out R> {
    data class Success<out T>(val value: T?) : RemoteData<T>()
    data class Error(val code: Int? = null, val error: ErrorData? = null) : RemoteData<Nothing>()
    object RemoteErrorByNetwork : RemoteData<Nothing>()
    object Loading : RemoteData<Nothing>()
}
