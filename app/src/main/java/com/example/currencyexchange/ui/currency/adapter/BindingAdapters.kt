package com.example.currencyexchange.ui.currency.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.currencyexchange.data.model.ConversionModel

object BindingAdapters {

    @BindingAdapter("setFromCurrency")
    @JvmStatic
    fun setFromCurrency(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            "${data.currencyToConvert} ${data.currencyToConvertType}".also { textView.text = it }
        }
    }

    @BindingAdapter("setToCurrency")
    @JvmStatic
    fun setToCurrency(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            "${data.currencyConverted} ${data.currencyConvertedType}".also { textView.text = it }
        }
    }

    @BindingAdapter("setCurrencyConversion")
    @JvmStatic
    fun setCurrencyConversion(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            "You are about to get ${data.currencyConverted} ${data.currencyConvertedType} for ${data.currencyToConvert} ${data.currencyToConvertType}. Do you approve this transaction ?".also { textView.text = it }
        }
    }

    @BindingAdapter("accountCredited")
    @JvmStatic
    fun accountCredited(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            "Great now you have ${data.currencyConverted} ${data.currencyConvertedType} in your account".also { textView.text = it }
        }
    }

    @BindingAdapter("yourConversionRate")
    @JvmStatic
    fun yourConversionRate(textView: AppCompatTextView, data:ConversionModel?) {
        data?.let {
            "Your conversion rate was 1 / ${"%.4f".format(data.conversionRate)}".also { textView.text = it }
        }
    }
}